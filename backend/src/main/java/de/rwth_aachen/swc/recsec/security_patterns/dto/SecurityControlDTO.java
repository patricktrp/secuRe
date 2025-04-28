package de.rwth_aachen.swc.recsec.security_patterns.dto;

/**
 * Data Transfer Object (DTO) for Security Control.
 *
 * This DTO encapsulates the minimal data required to represent a security control
 * in external communications, such as API responses.
 */
public record SecurityControlDTO(

        /**
         * The unique identifier of the security control.
         */
        Long id,

        /**
         * The type of the security control, represented as a string.
         */
        String type

) {
}