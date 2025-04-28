package de.rwth_aachen.swc.recsec.security_patterns.service;

import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;

import java.util.List;

/**
 * Service interface for managing security patterns.
 *
 * This interface defines methods to:
 * - Retrieve all available security patterns.
 * - Retrieve security patterns by their associated security control ID.
 * - Retrieve a specific security pattern by its unique ID.
 */
public interface ISecurityPatternService {

    /**
     * Retrieves all available security patterns.
     *
     * @return A list of SecurityPatternDTO objects representing all security patterns.
     */
    List<SecurityPatternDTO> getSecurityPatterns();

    /**
     * Retrieves security patterns associated with a specific security control ID.
     *
     * @param securityControlId The ID of the security control to filter patterns by.
     * @return A list of SecurityPatternDTO objects associated with the given security control ID.
     */
    List<SecurityPatternDTO> getSecurityPatternsBySecurityControlId(Long securityControlId);

    /**
     * Retrieves a specific security pattern by its unique ID.
     *
     * @param securityPatternId The unique identifier of the security pattern.
     * @return A SecurityPatternDTO object representing the security pattern,
     *         or null if no pattern is found with the given ID.
     */
    SecurityPatternDTO getSecurityPatternById(Long securityPatternId);
}