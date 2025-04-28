package de.rwth_aachen.swc.recsec.recommendations.core.dto;

import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectDTO;
import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;

import java.util.Map;

/**
 * Represents the context required for generating security pattern recommendations.
 *
 * This record encapsulates the security pattern being evaluated, the associated project details,
 * and user-defined preferences, which serve as inputs for the recommendation logic.
 *
 * @param securityPattern the security pattern being evaluated
 * @param project the project context, including its properties and constraints
 * @param userPreferences a map of user-defined preferences or constraints to refine the recommendation
 */
public record RecommendationContext(
        SecurityPatternDTO securityPattern,
        ProjectDTO project,
        Map<String, Object> userPreferences
) {}