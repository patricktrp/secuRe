package de.rwth_aachen.swc.recsec.recommendations.core.constraints;

import de.rwth_aachen.swc.recsec.recommendations.core.dto.RecommendationContext;

/**
 * Abstract base class representing a constraint in the recommendation system.
 *
 * A constraint evaluates whether a specific security pattern satisfies certain conditions
 * within the context of a recommendation. It supports both "hard" (mandatory) and "soft" (weighted) constraints.
 */
public abstract class Constraint {

    /**
     * Evaluates the constraint against the given recommendation context.
     *
     * @param recommendationContext the context for the recommendation, including security pattern and project details
     * @return a {@link ConstraintResult} indicating the applicability, satisfaction, and explanation of the evaluation
     */
    public ConstraintResult evaluate(RecommendationContext recommendationContext) {
        if (!isApplicable(recommendationContext)) {
            return new ConstraintResult(false, true, getInapplicableExplanation(recommendationContext));
        }

        boolean satisfied = isSatisfied(recommendationContext);
        String explanation = satisfied
                ? getSatisfiedExplanation(recommendationContext)
                : getViolatedExplanation(recommendationContext);

        return new ConstraintResult(true, satisfied, explanation);
    }

    /**
     * Gets the name of the constraint.
     *
     * @return a short, descriptive name for the constraint
     */
    public abstract String getName();

    /**
     * Gets the description of the constraint.
     *
     * @return a detailed explanation of the constraint's purpose and behavior
     */
    public abstract String getDescription();

    /**
     * Indicates whether the constraint is "hard."
     *
     * @return true if the constraint is mandatory; false if it is soft (scored)
     */
    public abstract boolean isHard();

    /**
     * Gets the weight of the constraint for scoring purposes.
     *
     * This is relevant for soft constraints.
     *
     * @return an integer representing the constraint's weight
     */
    public abstract int getWeight();

    /**
     * Determines whether the constraint is applicable to the given context.
     *
     * @param recommendationContext the recommendation context
     * @return true if the constraint applies; false otherwise
     */
    protected abstract boolean isApplicable(RecommendationContext recommendationContext);

    /**
     * Determines whether the constraint is satisfied for the given context.
     *
     * @param recommendationContext the recommendation context
     * @return true if the constraint is satisfied; false otherwise
     */
    protected abstract boolean isSatisfied(RecommendationContext recommendationContext);

    /**
     * Provides an explanation for a satisfied constraint.
     *
     * @param recommendationContext the recommendation context
     * @return a string explaining why the constraint is satisfied
     */
    protected abstract String getSatisfiedExplanation(RecommendationContext recommendationContext);

    /**
     * Provides an explanation for a violated constraint.
     *
     * @param recommendationContext the recommendation context
     * @return a string explaining why the constraint is violated
     */
    protected abstract String getViolatedExplanation(RecommendationContext recommendationContext);

    /**
     * Provides an explanation for an inapplicable constraint.
     *
     * @param recommendationContext the recommendation context
     * @return a default string explaining why the constraint is not applicable
     */
    protected String getInapplicableExplanation(RecommendationContext recommendationContext) {
        return "Constraint not applicable";
    }
}