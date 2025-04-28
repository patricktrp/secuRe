package de.rwth_aachen.swc.recsec.projects.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

/**
 * Represents a security requirement associated with a project.
 * Contains details such as title, description, timestamps, and the relationship to its project.
 */
@Getter
@Setter
@Entity
@Table(name = "project_security_requirements")
public class ProjectSecurityRequirement {

    /**
     * The unique identifier for the security requirement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_security_requirements_id_seq")
    @SequenceGenerator(name = "project_security_requirements_id_seq", sequenceName = "project_security_requirements_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The title of the security requirement.
     * Maximum length: 255 characters.
     */
    @Size(max = 255)
    @Column(name = "title")
    private String title;

    /**
     * The description of the security requirement.
     * Stored as a long text in the database.
     */
    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    /**
     * The timestamp when the security requirement was created.
     * Automatically set during entity persistence.
     */
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    /**
     * The timestamp when the security requirement was last updated.
     * Automatically updated during entity update.
     */
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    /**
     * The project to which this security requirement belongs.
     * Cascades deletion of the requirement when the associated project is deleted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "project_id")
    private Project project;

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