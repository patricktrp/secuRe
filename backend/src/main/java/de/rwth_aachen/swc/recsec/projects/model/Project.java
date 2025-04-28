package de.rwth_aachen.swc.recsec.projects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a project entity in the system.
 * Contains details about the project, its owner, metadata, and related security requirements.
 */
@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {

    /**
     * The unique identifier for the project.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projects_id_gen")
    @SequenceGenerator(name = "projects_id_gen", sequenceName = "projects_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The UUID of the user who owns the project.
     */
    @NotNull
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /**
     * The name of the project.
     * Maximum length: 255 characters.
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * A JSON field to store project-specific properties as key-value pairs.
     */
    @Column(name = "properties")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> properties;

    /**
     * The timestamp when the project was created.
     * Automatically set during entity persistence.
     */
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    /**
     * The timestamp when the project was last updated.
     * Automatically updated during entity update.
     */
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    /**
     * The set of security requirements associated with the project.
     * Cascade all operations and remove orphaned entities.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectSecurityRequirement> projectSecurityRequirements = new LinkedHashSet<>();

    /**
     * Sets the creation and update timestamps before persisting the entity.
     */
    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Updates the timestamp when the entity is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}