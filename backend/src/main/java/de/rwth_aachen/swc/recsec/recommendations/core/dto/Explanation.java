package de.rwth_aachen.swc.recsec.recommendations.core.dto;

/**
 * Represents the evaluation details of a specific constraint.
 *
 * This record encapsulates information about a constraint, including its name,
 * description, type (hard or soft), applicability, satisfaction status, and the rationale behind the evaluation result.
 *
 * @param constraintName the name of the constraint
 * @param constraintDescription a brief description of the constraint's purpose or behavior
 * @param isHard indicates whether the constraint is a hard constraint (true) or a soft constraint (false)
 * @param applicable indicates whether the constraint was applicable to the given context
 * @param satisfied indicates whether the constraint was satisfied during evaluation
 * @param rationale a textual explanation or rationale for the constraint's satisfaction status
 */
public record Explanation(
        String constraintName,
        String constraintDescription,
        boolean isHard,
        boolean applicable,
        boolean satisfied,
        String rationale
) {}