package de.rwth_aachen.swc.recsec.projects.service;

import de.rwth_aachen.swc.recsec.projects.dto.requests.ProjectCreationRequest;
import de.rwth_aachen.swc.recsec.projects.dto.requests.SecurityRequirementCreationRequest;
import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectDTO;
import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectOverviewDTO;
import de.rwth_aachen.swc.recsec.projects.dto.responses.SecurityRequirementDTO;

import java.util.List;

/**
 * Service interface for managing projects and their security requirements.
 * Defines operations for creating, retrieving, updating, and deleting projects and security requirements.
 */
public interface IProjectService {

    /**
     * Retrieves all projects for a given user.
     *
     * @param userId the ID of the user.
     * @return a list of project overviews associated with the user.
     */
    List<ProjectOverviewDTO> getProjects(String userId);

    /**
     * Retrieves detailed information about a specific project.
     *
     * @param userId    the ID of the user.
     * @param projectId the ID of the project.
     * @return the details of the specified project.
     */
    ProjectDTO getProjectById(String userId, Long projectId);

    /**
     * Creates a new project for a user.
     *
     * @param userId the ID of the user.
     * @param request the project creation request containing project details.
     */
    void createProject(String userId, ProjectCreationRequest request);

    /**
     * Deletes a specific project for a user.
     *
     * @param userId    the ID of the user.
     * @param projectId the ID of the project to delete.
     */
    void deleteProject(String userId, Long projectId);

    /**
     * Creates a new security requirement for a project.
     *
     * @param userId  the ID of the user.
     * @param projectId the ID of the project to which the security requirement will be added.
     * @param request the security requirement creation request containing the details of the requirement.
     */
    void createSecurityRequirement(String userId, Long projectId, SecurityRequirementCreationRequest request);

    /**
     * Deletes a specific security requirement from a project.
     *
     * @param userId              the ID of the user.
     * @param projectId           the ID of the project containing the security requirement.
     * @param securityRequirementId the ID of the security requirement to delete.
     */
    void deleteSecurityRequirementById(String userId, Long projectId, Long securityRequirementId);

    /**
     * Retrieves detailed information about a specific security requirement in a project.
     *
     * @param userId              the ID of the user.
     * @param projectId           the ID of the project containing the security requirement.
     * @param securityRequirementId the ID of the security requirement.
     * @return the details of the specified security requirement.
     */
    SecurityRequirementDTO getSecurityRequirementById(String userId, Long projectId, Long securityRequirementId);

    /**
     * Updates a specific security requirement in a project.
     *
     * @param userId              the ID of the user.
     * @param projectId           the ID of the project containing the security requirement.
     * @param securityRequirementId the ID of the security requirement to update.
     * @param request             the updated details of the security requirement.
     */
    void updateSecurityRequirementById(String userId, Long projectId, Long securityRequirementId, SecurityRequirementCreationRequest request);
}