package de.rwth_aachen.swc.recsec.projects.controller;

import de.rwth_aachen.swc.recsec.projects.dto.requests.ProjectCreationRequest;
import de.rwth_aachen.swc.recsec.projects.dto.requests.SecurityRequirementCreationRequest;
import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectDTO;
import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectOverviewDTO;
import de.rwth_aachen.swc.recsec.projects.service.IProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller class for managing project-related operations.
 * Provides endpoints to create, retrieve, update, and delete projects
 * and their associated security requirements.
 */
@RestController
@RequestMapping("/projects")
@Slf4j
@Tag(name = "Projects", description = "Endpoints for managing projects and their security requirements")
public class ProjectController {

    private final IProjectService projectService;

    /**
     * Constructor for ProjectController.
     *
     * @param projectService the service handling project-related operations.
     */
    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Retrieves all projects associated with the authenticated user.
     *
     * @param principal the authenticated user.
     * @return a list of project overviews for the user.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all projects", description = "Retrieves all projects for the authenticated user")
    public List<ProjectOverviewDTO> getProjects(
            @Parameter(hidden = true) Principal principal
    ) {
        String userId = principal.getName();
        log.info("Getting projects for user: {}", userId);
        return projectService.getProjects(userId);
    }

    /**
     * Retrieves detailed information about a specific project.
     *
     * @param principal the authenticated user.
     * @param projectId the ID of the project to retrieve.
     * @return a {@link ProjectDTO}
     */
    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get project by ID", description = "Retrieves detailed information about a specific project")
    public ProjectDTO getProjectById(
            @Parameter(hidden = true) Principal principal,
            @Parameter(description = "The ID of the project to retrieve", required = true) @PathVariable Long projectId
    ) {
        String userId = principal.getName();
        log.info("Getting project with id={} for user: {}", projectId, userId);
        return projectService.getProjectById(userId, projectId);
    }

    /**
     * Creates a new project for the authenticated user.
     *
     * @param principal the authenticated user.
     * @param request   the details of the project to create.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new project", description = "Creates a new project for the authenticated user")
    public void createProject(
            @Parameter(hidden = true) Principal principal,
            @Parameter(description = "The project creation request", required = true) @RequestBody ProjectCreationRequest request
    ) {
        String userId = principal.getName();
        log.info("Project creation requested by user: {}", userId);
        projectService.createProject(userId, request);
    }

    /**
     * Deletes a specific project owned by the authenticated user.
     *
     * @param principal the authenticated user.
     * @param projectId the ID of the project to delete.
     */
    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a project", description = "Deletes a specific project owned by the authenticated user")
    public void deleteProjectById(
            @Parameter(hidden = true) Principal principal,
            @Parameter(description = "The ID of the project to delete", required = true) @PathVariable Long projectId
    ) {
        String userId = principal.getName();
        log.info("Deleting project with id={} for user: {}", projectId, userId);
        projectService.deleteProject(userId, projectId);
    }

    /**
     * Adds a new security requirement to a specific project.
     *
     * @param principal the authenticated user.
     * @param projectId the ID of the project to which the security requirement will be added.
     * @param request   the details of the security requirement to add.
     */
    @PostMapping("/{projectId}/security-requirements")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a security requirement", description = "Adds a new security requirement to a specific project")
    public void createSecurityRequirement(
            @Parameter(hidden = true) Principal principal,
            @Parameter(description = "The ID of the project to add the security requirement to", required = true) @PathVariable Long projectId,
            @Parameter(description = "The security requirement creation request", required = true) @RequestBody SecurityRequirementCreationRequest request
    ) {
        String userId = principal.getName();
        log.info("Creating security requirement for projectId={} by user: {}", projectId, userId);
        projectService.createSecurityRequirement(userId, projectId, request);
    }

    /**
     * Deletes a specific security requirement from a project.
     *
     * @param principal            the authenticated user.
     * @param projectId            the ID of the project containing the security requirement.
     * @param securityRequirementId the ID of the security requirement to delete.
     */
    @DeleteMapping("/{projectId}/security-requirements/{securityRequirementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a security requirement", description = "Deletes a specific security requirement from a project")
    public void deleteSecurityRequirementById(
            @Parameter(hidden = true) Principal principal,
            @Parameter(description = "The ID of the project containing the security requirement", required = true) @PathVariable Long projectId,
            @Parameter(description = "The ID of the security requirement to delete", required = true) @PathVariable Long securityRequirementId
    ) {
        String userId = principal.getName();
        log.info("Deleting security requirement with id={} for projectId={} by user: {}", securityRequirementId, projectId, userId);
        projectService.deleteSecurityRequirementById(userId, projectId, securityRequirementId);
    }

    /**
     * Updates a specific security requirement in a project.
     *
     * @param principal            the authenticated user.
     * @param projectId            the ID of the project containing the security requirement.
     * @param securityRequirementId the ID of the security requirement to update.
     * @param request              the updated details of the security requirement.
     */
    @PutMapping("/{projectId}/security-requirements/{securityRequirementId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a security requirement", description = "Updates a specific security requirement in a project")
    public void updateSecurityRequirementById(
            @Parameter(hidden = true) Principal principal,
            @Parameter(description = "The ID of the project containing the security requirement", required = true) @PathVariable Long projectId,
            @Parameter(description = "The ID of the security requirement to update", required = true) @PathVariable Long securityRequirementId,
            @Parameter(description = "The updated security requirement details", required = true) @RequestBody SecurityRequirementCreationRequest request
    ) {
        String userId = principal.getName();
        log.info("Updating security requirement with id={} for projectId={} by user: {}", securityRequirementId, projectId, userId);
        projectService.updateSecurityRequirementById(userId, projectId, securityRequirementId, request);
    }
}