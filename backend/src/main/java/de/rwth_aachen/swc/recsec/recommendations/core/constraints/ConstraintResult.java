package de.rwth_aachen.swc.recsec.recommendations.core.constraints;

/**
 * Represents the result of evaluating a single constraint.
 *
 * This record encapsulates whether the constraint was applicable, whether it was satisfied,
 * and an explanation providing details about the evaluation.
 *
 * @param applicable indicates whether the constraint was applicable in the given context
 * @param satisfied indicates whether the constraint was satisfied
 * @param explanation a textual explanation describing the result of the constraint evaluation
 */
public record ConstraintResult(
        boolean applicable,
        boolean satisfied,
        String explanation
) { }