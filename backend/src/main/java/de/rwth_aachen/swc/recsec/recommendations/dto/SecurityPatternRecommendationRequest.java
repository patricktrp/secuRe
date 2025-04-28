package de.rwth_aachen.swc.recsec.recommendations.dto;

import java.util.Map;

/**
 * Data Transfer Object (DTO) representing a request for security pattern recommendations.
 *
 * This record encapsulates the context and user preferences required to generate
 * security pattern recommendations.
 *
 * @param securityControlId the ID of the security control associated with the request
 * @param projectId the ID of the project for which recommendations are being requested
 * @param userPreferences a map containing user preferences or additional parameters to influence recommendations
 */
public record SecurityPatternRecommendationRequest(
        Long securityControlId,
        Long projectId,
        Map<String, Object> userPreferences
) {}