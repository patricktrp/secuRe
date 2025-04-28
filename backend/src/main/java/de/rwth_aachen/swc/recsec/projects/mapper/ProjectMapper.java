package de.rwth_aachen.swc.recsec.projects.mapper;

import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectDTO;
import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectOverviewDTO;
import de.rwth_aachen.swc.recsec.projects.dto.responses.SecurityRequirementDTO;
import de.rwth_aachen.swc.recsec.projects.model.Project;
import de.rwth_aachen.swc.recsec.projects.model.ProjectSecurityRequirement;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting entities to DTOs.
 * Handles transformations between Project and related entities and their corresponding DTOs.
 */
@Component
public class ProjectMapper {

    /**
     * Maps a {@link Project} entity to a {@link ProjectDTO}.
     *
     * @param project the project entity to map.
     * @return a {@link ProjectDTO} containing detailed information about the project.
     */
    public ProjectDTO mapProjectToDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getProperties(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                project.getProjectSecurityRequirements().stream()
                        .map(this::mapProjectSecurityRequirementToDTO)
                        .toList()
        );
    }

    /**
     * Maps a {@link Project} entity to a {@link ProjectOverviewDTO}.
     * Provides a lightweight representation of the project.
     *
     * @param project the project entity to map.
     * @return a {@link ProjectOverviewDTO} containing summary information about the project.
     */
    public ProjectOverviewDTO mapProjectToOverviewDTO(Project project) {
        return new ProjectOverviewDTO(
                project.getId(),
                project.getName(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    /**
     * Maps a {@link ProjectSecurityRequirement} entity to a {@link SecurityRequirementDTO}.
     *
     * @param projectSecurityRequirement the security requirement entity to map.
     * @return a {@link SecurityRequirementDTO} containing details of the security requirement.
     */
    public SecurityRequirementDTO mapProjectSecurityRequirementToDTO(ProjectSecurityRequirement projectSecurityRequirement) {
        return new SecurityRequirementDTO(
                projectSecurityRequirement.getId(),
                projectSecurityRequirement.getTitle(),
                projectSecurityRequirement.getDescription()
        );
    }
}