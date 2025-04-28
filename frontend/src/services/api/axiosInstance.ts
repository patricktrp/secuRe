import axios from "axios";
import useAuthStore from "@/store/useAuthStore";

// Create axios instance with a base URL
const axiosInstance = axios.create({
  baseURL: "http://localhost:8080",
});

// Add a request interceptor to attach the access token if available
axiosInstance.interceptors.request.use(
  (config) => {
    const { accessToken } = useAuthStore.getState();

    // If the access token exists, add it to the Authorization header
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }

    return config;
  },
  (error) => {
    // Handle any error that happens before the request is sent
    return Promise.reject(error);
  }
);

export default axiosInstance;
