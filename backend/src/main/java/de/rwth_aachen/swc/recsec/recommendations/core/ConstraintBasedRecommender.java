package de.rwth_aachen.swc.recsec.recommendations.core;

import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectDTO;
import de.rwth_aachen.swc.recsec.recommendations.core.constraints.ConstraintEvaluator;
import de.rwth_aachen.swc.recsec.recommendations.core.constraints.ConstraintLoader;
import de.rwth_aachen.swc.recsec.recommendations.core.constraints.EvaluationResult;
import de.rwth_aachen.swc.recsec.recommendations.core.constraints.SpelConstraint;
import de.rwth_aachen.swc.recsec.recommendations.core.dto.RecommendationContext;
import de.rwth_aachen.swc.recsec.recommendations.core.dto.SecurityPatternRecommendation;
import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link IRecommender} interface using a constraint-based approach.
 *
 * This class evaluates security patterns against constraints to generate recommendations.
 * Constraints are divided into "hard" (mandatory) and "soft" (scoring) types.
 */
@Component
@Slf4j
public class ConstraintBasedRecommender implements IRecommender {

    private final ConstraintLoader constraintLoader;
    private final ConstraintEvaluator constraintEvaluator;

    /**
     * The maximum number of recommendations to return.
     * Configurable via the application properties (`recommendation.top-k`).
     */
    @Value("${recommendation.top-k:10}")
    private int topK;

    /**
     * Constructs the ConstraintBasedRecommender with its dependencies.
     *
     * @param constraintLoader the loader for retrieving constraints
     * @param constraintEvaluator the evaluator for processing constraints against a context
     */
    public ConstraintBasedRecommender(ConstraintLoader constraintLoader, ConstraintEvaluator constraintEvaluator) {
        this.constraintLoader = constraintLoader;
        this.constraintEvaluator = constraintEvaluator;
    }

    /**
     * Generates a list of security pattern recommendations based on the given parameters.
     *
     * @param securityControlId the ID of the security control
     * @param securityPatterns the list of available {@link SecurityPatternDTO} objects
     * @param project the project context, including its properties and constraints
     * @param userPreferences a map of user-defined preferences for customization
     * @return a list of top-ranked {@link SecurityPatternRecommendation} objects
     */
    @Override
    public List<SecurityPatternRecommendation> getSecurityPatternRecommendations(
            Long securityControlId,
            List<SecurityPatternDTO> securityPatterns,
            ProjectDTO project,
            Map<String, Object> userPreferences) {

        log.info("Generating recommendations for security control ID: {}", securityControlId);

        // Load constraints for the specified security control
        List<SpelConstraint> constraints = constraintLoader.loadConstraintsBySecurityControlId(securityControlId);
        List<SecurityPatternRecommendation> recommendations = new ArrayList<>();

        // Evaluate each security pattern against the constraints
        for (SecurityPatternDTO securityPattern : securityPatterns) {
            RecommendationContext context = new RecommendationContext(securityPattern, project, userPreferences);

            // Evaluate the current context
            EvaluationResult evaluationResult = constraintEvaluator.evaluate(constraints, context);

            // Skip patterns that fail to satisfy all hard constraints
            if (!evaluationResult.allHardSatisfied()) {
                log.debug("Security pattern '{}' failed hard constraints.", securityPattern.name());
                continue;
            }

            // Add recommendation for patterns satisfying hard constraints
            SecurityPatternRecommendation recommendation = new SecurityPatternRecommendation(
                    securityPattern,
                    evaluationResult.softScore(),
                    evaluationResult.explanations()
            );
            recommendations.add(recommendation);
        }

        // Sort recommendations by score in descending order and return the top-K
        return recommendations.stream()
                .sorted((r1, r2) -> Double.compare(r2.score(), r1.score()))
                .limit(topK)
                .toList();
    }
}