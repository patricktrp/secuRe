package de.rwth_aachen.swc.recsec.recommendations.core.constraints;

import de.rwth_aachen.swc.recsec.recommendations.core.dto.RecommendationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * A constraint implementation based on Spring Expression Language (SpEL).
 *
 * This class allows dynamic evaluation of constraints using SpEL expressions.
 * Constraints can define their applicability, satisfaction, and explanatory logic.
 */
public class SpelConstraint extends Constraint {

    private final String applicabilityExpression;
    private final String satisfactionExpression;
    private final String satisfiedExplanationExpression;
    private final String violatedExplanationExpression;
    private final int weight;
    private final boolean isHard;
    private final String name;
    private final String description;

    /**
     * Constructs a {@code SpelConstraint} with the given expressions and metadata.
     *
     * @param applicabilityExpression SpEL expression to evaluate if the constraint is applicable
     * @param satisfactionExpression SpEL expression to determine if the constraint is satisfied
     * @param explanationExpression SpEL expression for the explanation when the constraint is satisfied
     * @param violatedExplanationExpression SpEL expression for the explanation when the constraint is violated
     * @param weight the weight of the constraint, used for scoring (soft constraints only)
     * @param isHard {@code true} if the constraint is mandatory; {@code false} if it is soft
     * @param name the name of the constraint
     * @param description a description of the constraint's purpose
     */
    public SpelConstraint(
            String applicabilityExpression,
            String satisfactionExpression,
            String explanationExpression,
            String violatedExplanationExpression,
            int weight,
            boolean isHard,
            String name,
            String description) {
        this.applicabilityExpression = applicabilityExpression;
        this.satisfactionExpression = satisfactionExpression;
        this.satisfiedExplanationExpression = explanationExpression;
        this.violatedExplanationExpression = violatedExplanationExpression;
        this.weight = weight;
        this.isHard = isHard;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean isHard() {
        return isHard;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    protected boolean isApplicable(RecommendationContext context) {
        return evaluateExpression(applicabilityExpression, context, Boolean.class);
    }

    @Override
    protected boolean isSatisfied(RecommendationContext context) {
        return evaluateExpression(satisfactionExpression, context, Boolean.class);
    }

    @Override
    protected String getSatisfiedExplanation(RecommendationContext context) {
        return evaluateExpression(satisfiedExplanationExpression, context, String.class);
    }

    @Override
    protected String getViolatedExplanation(RecommendationContext context) {
        return evaluateExpression(violatedExplanationExpression, context, String.class);
    }

    /**
     * Evaluates a given SpEL expression against the provided recommendation context.
     *
     * @param expressionString the SpEL expression to evaluate
     * @param context the context containing data for evaluation
     * @param returnType the expected return type of the expression
     * @param <T> the type of the evaluated result
     * @return the result of the evaluated expression
     */
    private <T> T evaluateExpression(String expressionString, RecommendationContext context, Class<T> returnType) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext evalContext = new StandardEvaluationContext(context);
        evalContext.setRootObject(context);
        return parser.parseExpression(expressionString).getValue(evalContext, returnType);
    }
}