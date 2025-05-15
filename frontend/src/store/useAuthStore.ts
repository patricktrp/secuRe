import { create } from "zustand";
import Keycloak from "keycloak-js";

// Initialize Keycloak instance with configuration
const keycloak = new Keycloak({
  url: "http://localhost:8081",
  realm: "RecSec",
  clientId: "webapp",
});

// Define User type
type User = {
  email: string;
  firstName: string;
  lastName: string;
};

// Define AuthState type for the Zustand store
interface AuthState {
  accessToken: string | null;
  user: User;
  logout: () => Promise<void>;
  initializeKeycloak: () => Promise<void>;
}

// Default user object when not authenticated
const defaultUser: User = {
  email: "",
  firstName: "",
  lastName: "",
};

// Zustand store definition
const useAuthStore = create<AuthState>((set) => {
  // Initialize Keycloak
  const initializeKeycloak = async () => {
    try {
      // Try initializing Keycloak and check authentication status
      const authenticated = await keycloak.init({
        onLoad: "login-required", // Force login on load
      });

      if (authenticated && keycloak.idTokenParsed) {
        // If authenticated, set user data and access token
        const user: User = {
          email: keycloak.idTokenParsed.email || "",
          firstName: keycloak.idTokenParsed.given_name || "",
          lastName: keycloak.idTokenParsed.family_name || "",
        };
        set({ accessToken: keycloak.token, user });
      } else {
        // Warn if the user is not authenticated and reset state
        console.warn("User is not authenticated");
        set({ accessToken: null, user: defaultUser });
      }
    } catch (error) {
      console.error("Failed to initialize Keycloak", error);
      // In case of error, reset to default user
      set({ accessToken: null, user: defaultUser });
    }
  };

  // Logout function
  const logout = async () => {
    try {
      await keycloak.logout({
        redirectUri: window.location.origin, // Redirect after logout
      });
      set({ accessToken: null, user: defaultUser });
    } catch (error) {
      console.error("Failed to log out", error);
    }
  };

  // Initialize Keycloak on store creation
  initializeKeycloak();

  // Return initial state and functions
  return {
    accessToken: null, // Initial token is null
    user: defaultUser, // Default user state
    logout,
    initializeKeycloak, // Allow re-initialization if necessary
  };
});

export default useAuthStore;
