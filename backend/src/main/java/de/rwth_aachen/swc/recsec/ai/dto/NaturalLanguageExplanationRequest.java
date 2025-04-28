package de.rwth_aachen.swc.recsec.ai.dto;

import de.rwth_aachen.swc.recsec.recommendations.core.dto.Explanation;
import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;

import java.util.List;

/**
 * A request object for generating a natural language explanation.
 *
 * This record encapsulates a security pattern and a list of related explanations
 * or constraints, which are used to generate a natural language description.
 *
 * @param securityPatternDTO the security pattern for which an explanation is generated
 * @param explanations a list of related constraints or explanations to include in the response
 */
public record NaturalLanguageExplanationRequest(
        SecurityPatternDTO securityPatternDTO,
        List<Explanation> explanations
) {
}