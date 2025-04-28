package de.rwth_aachen.swc.recsec.security_patterns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity representing a Security Control.
 *
 * A Security Control defines a specific mechanism or strategy for ensuring
 * the security of a software system. This entity maps to the "security_controls"
 * table in the database and establishes relationships with related entities like
 * {@link SecurityPattern}.
 */
@Getter
@Setter
@Entity
@Table(name = "security_controls")
public class SecurityControl {

    /**
     * Unique identifier for the security control.
     *
     * Uses a sequence generator for generating IDs.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_controls_id_gen")
    @SequenceGenerator(
            name = "security_controls_id_gen",
            sequenceName = "security_controls_id_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The type of security control.
     *
     * This is represented as an enum and must not be null.
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SecurityControlType type;

    /**
     * A detailed description of the security control.
     */
    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    /**
     * A set of {@link SecurityPattern} entities associated with this security control.
     *
     * Represents a one-to-many relationship. Fetching is done lazily to optimize performance.
     */
    @OneToMany(mappedBy = "securityControl", fetch = FetchType.LAZY)
    private Set<SecurityPattern> securityPatterns = new LinkedHashSet<>();
}