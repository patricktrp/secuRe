package de.rwth_aachen.swc.recsec.security_patterns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.util.Map;

/**
 * Entity representing a Security Pattern.
 *
 * This entity is used to define the attributes and relationships of a security pattern
 * in the system. It maps to the "security_patterns" database table.
 */
@Getter
@Setter
@Entity
@Table(name = "security_patterns")
public class SecurityPattern {

    /**
     * Unique identifier for the security pattern.
     *
     * Uses a sequence generator for ID generation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_patterns_id_gen")
    @SequenceGenerator(
            name = "security_patterns_id_gen",
            sequenceName = "security_patterns_id_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Name of the security pattern.
     *
     * Must not be null and has a maximum length of 255 characters.
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Detailed description of the security pattern.
     *
     * Stored as a JSON object in the database.
     */
    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> description;

    /**
     * Additional properties of the security pattern.
     *
     * Stored as a JSON object in the database.
     */
    @Column(name = "properties")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> properties;

    /**
     * The associated security control for this security pattern.
     *
     * Represents a many-to-one relationship. When the associated security control is deleted,
     * this security pattern will also be removed (cascading delete).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "security_control")
    private SecurityControl securityControl;
}