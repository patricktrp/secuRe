package de.rwth_aachen.swc.recsec.recommendations.core.constraints;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

/**
 * Entity representing a constraint in the recommendation system.
 *
 * This class is mapped to the `constraints` table in the database and defines the properties
 * for a constraint, including expressions for applicability, satisfaction, and explanations.
 */
@Getter
@Setter
@Entity
@Table(name = "constraints")
public class ConstraintEntity {

    /**
     * The unique identifier for the constraint.
     * This is the primary key and is generated using a sequence.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "constraints_id_gen")
    @SequenceGenerator(name = "constraints_id_gen", sequenceName = "constraints_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The name of the constraint. This is a required field with a maximum length of 255 characters.
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * A detailed description of the constraint.
     * This field can be of arbitrary length.
     */
    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    /**
     * A SpEL expression that determines if the constraint is applicable in the given context.
     * This is a required field.
     */
    @NotNull
    @Column(name = "applicability_expression", nullable = false, length = Integer.MAX_VALUE)
    private String applicabilityExpression;

    /**
     * A SpEL expression to check if the constraint is satisfied.
     * This is a required field.
     */
    @NotNull
    @Column(name = "satisfaction_expression", nullable = false, length = Integer.MAX_VALUE)
    private String satisfactionExpression;

    /**
     * A SpEL expression for generating an explanation when the constraint is satisfied.
     * This is a required field.
     */
    @NotNull
    @Column(name = "satisfied_explanation_expression", nullable = false, length = Integer.MAX_VALUE)
    private String satisfiedExplanationExpression;

    /**
     * A SpEL expression for generating an explanation when the constraint is violated.
     * This is a required field.
     */
    @NotNull
    @Column(name = "violated_explanation_expression", nullable = false, length = Integer.MAX_VALUE)
    private String violatedExplanationExpression;

    /**
     * The weight of the constraint. This is a required field with a default value of 1.
     */
    @NotNull
    @ColumnDefault("1")
    @Column(name = "weight", nullable = false)
    private Integer weight;

    /**
     * A flag indicating whether the constraint is hard (mandatory) or soft (optional).
     * Default value is true (hard).
     */
    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_hard", nullable = false)
    private Boolean isHard = false;

    /**
     * Timestamp when the constraint was created.
     */
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    /**
     * Timestamp when the constraint was last updated.
     */
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    /**
     * The ID of the related security control for this constraint, if applicable.
     */
    @Column(name = "security_control_id")
    private Long securityControlId;

    /**
     * The ID of the related security pattern for this constraint, if applicable.
     */
    @Column(name = "security_pattern_id")
    private Long securityPatternId;
}