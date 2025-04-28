package de.rwth_aachen.swc.recsec.projects.repository;

import de.rwth_aachen.swc.recsec.projects.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Project entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Retrieves all projects associated with a specific user.
     *
     * @param userId the UUID of the user.
     * @return a list of projects owned by the user.
     */
    List<Project> findAllByUserId(UUID userId);

    /**
     * Finds a project by its ID and user ID.
     *
     * @param projectId the ID of the project.
     * @param userId    the UUID of the user who owns the project.
     * @return an Optional containing the project if found, or empty if not found.
     */
    Optional<Project> findByIdAndUserId(Long projectId, UUID userId);
}