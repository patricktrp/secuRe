package de.rwth_aachen.swc.recsec.security_patterns.dto;

import java.util.Map;

/**
 * Data Transfer Object (DTO) for Security Pattern.
 *
 * This class is used to transfer data related to security patterns between
 * different layers of the application, such as services and controllers.
 */
public record SecurityPatternDTO(

        /**
         * The unique identifier of the security pattern.
         */
        Long id,

        /**
         * The name of the security pattern.
         */
        String name,

        /**
         * A detailed description of the security pattern, represented as a map.
         */
        Map<String, Object> description,

        /**
         * A set of properties associated with the security pattern, represented as a map.
         */
        Map<String, Object> properties,

        /**
         * The associated {@link SecurityControlDTO} that defines the security control
         * linked to this security pattern.
         */
        SecurityControlDTO securityControl

) {}