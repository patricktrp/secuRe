package de.rwth_aachen.swc.recsec.recommendations.core.constraints;

import de.rwth_aachen.swc.recsec.recommendations.core.dto.Explanation;
import de.rwth_aachen.swc.recsec.recommendations.core.dto.RecommendationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluates a list of constraints against a given recommendation context.
 *
 * This component processes both hard and soft constraints and returns an evaluation result,
 * including whether all hard constraints were satisfied, a soft score, and detailed explanations.
 */
@Slf4j
@Component
public class ConstraintEvaluator {

    /**
     * Evaluates a list of constraints against the given recommendation context.
     *
     * @param constraints the list of constraints to evaluate
     * @param recommendationContext the context in which constraints are being evaluated, typically includes security patterns and project information
     * @return an {@link EvaluationResult} containing the evaluation outcome
     */
    public EvaluationResult evaluate(List<? extends Constraint> constraints, RecommendationContext recommendationContext) {
        boolean allHardSatisfied = true;  // Tracks if all hard constraints are satisfied
        int satisfiedWeight = 0;  // Cumulative weight of satisfied soft constraints
        int totalWeight = 0;  // Total weight of all soft constraints
        List<Explanation> explanations = new ArrayList<>();  // List of explanations for each constraint

        // Iterate over each constraint and evaluate its satisfaction
        for (Constraint constraint : constraints) {
            // Evaluate the constraint for the given context
            ConstraintResult result = constraint.evaluate(recommendationContext);

            // If the constraint is applicable, add its explanation to the list
            if (result.applicable()) {
                Explanation explanation = new Explanation(
                        constraint.getName(),
                        constraint.getDescription(),
                        constraint.isHard(),
                        result.applicable(),
                        result.satisfied(),
                        result.explanation()
                );
                explanations.add(explanation);
                log.error("APPLIED");
            }

            // Handle hard constraints
            if (constraint.isHard()) {
                if (!result.satisfied()) {
                    allHardSatisfied = false;  // If a hard constraint is not satisfied, stop further evaluation
                    break;
                }
            } else {
                // Handle soft constraints
                if (result.satisfied()) {
                    satisfiedWeight += constraint.getWeight();  // Add weight for satisfied soft constraints
                }
                totalWeight += constraint.getWeight();  // Accumulate total weight for all soft constraints
            }
        }

        // Calculate the soft score as the ratio of satisfied weight to total weight, defaulting to 1.0 if no soft constraints
        double softScore = totalWeight != 0 ? ((double) satisfiedWeight / totalWeight) : 1.0;

        // Return the evaluation result
        return new EvaluationResult(allHardSatisfied, softScore, explanations);
    }
}