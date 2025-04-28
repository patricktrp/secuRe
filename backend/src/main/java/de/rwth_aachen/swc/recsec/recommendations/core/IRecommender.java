package de.rwth_aachen.swc.recsec.recommendations.core;

import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectDTO;
import de.rwth_aachen.swc.recsec.recommendations.core.dto.SecurityPatternRecommendation;
import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;

import java.util.List;
import java.util.Map;

/**
 * Interface for recommendation logic.
 *
 * Defines methods for generating security pattern recommendations based on
 * project details, associated security controls, and user-defined preferences.
 */
public interface IRecommender {

    /**
     * Generates a list of security pattern recommendations based on the given parameters.
     *
     * @param securityControlId the ID of the security control related to the recommendation
     * @param securityPatterns a list of available {@link SecurityPatternDTO} objects to consider
     * @param project the project context, including its properties and constraints
     * @param userPreferences a map of user-defined preferences to refine recommendations
     * @return a list of {@link SecurityPatternRecommendation} objects
     */
    List<SecurityPatternRecommendation> getSecurityPatternRecommendations(
            Long securityControlId,
            List<SecurityPatternDTO> securityPatterns,
            ProjectDTO project,
            Map<String, Object> userPreferences);
}