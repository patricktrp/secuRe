package de.rwth_aachen.swc.recsec.security_patterns.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing the types of security controls.
 *
 * Each enum constant is associated with a display name, which provides
 * a user-friendly representation of the security control type.
 *
 * The enum also includes a lookup mechanism to retrieve an instance by its display name.
 */
@Getter
public enum SecurityControlType {

    /**
     * Represents an authentication-related security control.
     */
    AUTHENTICATION("Authentication");

    /**
     * The user-friendly name of the security control type.
     */
    private final String displayName;

    /**
     * A static map for quickly retrieving an enum instance by its display name.
     */
    private static final Map<String, SecurityControlType> lookup = new HashMap<>();

    // Static block to populate the lookup map with enum values
    static {
        for (SecurityControlType type : SecurityControlType.values()) {
            lookup.put(type.getDisplayName(), type);
        }
    }

    /**
     * Constructor to associate a display name with the enum constant.
     *
     * @param displayName the user-friendly name for the security control type.
     */
    SecurityControlType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retrieves a SecurityControlType instance by its display name.
     *
     * @param displayName the display name of the security control type.
     * @return the corresponding SecurityControlType instance, or null if no match is found.
     */
    public static SecurityControlType getByDisplayName(String displayName) {
        return lookup.get(displayName);
    }

    /**
     * Provides a string representation of the enum constant.
     *
     * @return the display name of the security control type.
     */
    @Override
    public String toString() {
        return displayName;
    }
}