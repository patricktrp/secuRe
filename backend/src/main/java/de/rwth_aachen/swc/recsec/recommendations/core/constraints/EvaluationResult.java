package de.rwth_aachen.swc.recsec.recommendations.core.constraints;

import de.rwth_aachen.swc.recsec.recommendations.core.dto.Explanation;

import java.util.List;

/**
 * Represents the result of evaluating constraints in the recommendation system.
 *
 * This record encapsulates whether all hard constraints are satisfied, the calculated soft score,
 * and a list of explanations providing detailed insights into the evaluation process.
 *
 * @param allHardSatisfied indicates whether all hard constraints are satisfied
 * @param softScore the cumulative score from soft constraints
 * @param explanations a list of {@link Explanation} objects detailing the evaluation results for each constraint
 */
public record EvaluationResult(
        boolean allHardSatisfied,
        double softScore,
        List<Explanation> explanations
) { }