package de.rwth_aachen.swc.recsec.recommendations.core.dto;

import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;

import java.util.List;

/**
 * Represents a recommended security pattern, including its evaluation score and explanations.
 *
 * This record encapsulates a security pattern that is recommended based on evaluation criteria,
 * along with the score and detailed explanations for the recommendation.
 *
 * @param securityPattern the recommended security pattern
 * @param score the evaluation score indicating the relevance or suitability of the pattern
 * @param explanations a list of {@link Explanation} objects detailing the evaluation process and rationale
 */
public record SecurityPatternRecommendation(
        SecurityPatternDTO securityPattern,
        double score,
        List<Explanation> explanations
) {}