package de.rwth_aachen.swc.recsec.projects.service;

import de.rwth_aachen.swc.recsec.projects.dto.requests.ProjectCreationRequest;
import de.rwth_aachen.swc.recsec.projects.dto.requests.SecurityRequirementCreationRequest;
import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectDTO;
import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectOverviewDTO;
import de.rwth_aachen.swc.recsec.projects.dto.responses.SecurityRequirementDTO;
import de.rwth_aachen.swc.recsec.projects.mapper.ProjectMapper;
import de.rwth_aachen.swc.recsec.projects.model.Project;
import de.rwth_aachen.swc.recsec.projects.model.ProjectSecurityRequirement;
import de.rwth_aachen.swc.recsec.projects.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation for managing projects and their security requirements.
 */
@Service
@Slf4j
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    /**
     * Constructor for ProjectService.
     *
     * @param projectRepository the repository to manage project data.
     * @param projectMapper     the mapper to transform project data to DTOs.
     */
    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<ProjectOverviewDTO> getProjects(String userId) {
        UUID userIdAsUUID = UUID.fromString(userId);
        return projectRepository.findAllByUserId(userIdAsUUID)
                .stream()
                .map(projectMapper::mapProjectToOverviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProjectById(String userId, Long projectId) {
        Project project = projectRepository.findByIdAndUserId(projectId, UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
        validateUserAccessToProject(UUID.fromString(userId), project);
        return projectMapper.mapProjectToDTO(project);
    }

    @Override
    public void createProject(String userId, ProjectCreationRequest projectCreationRequest) {
        try {
            log.info("Creating new project: {}", projectCreationRequest);
            Project project = new Project();
            project.setUserId(UUID.fromString(userId));
            project.setName(projectCreationRequest.name());
            project.setProperties(projectCreationRequest.properties());
            log.error("{}", project.getProperties());
            projectRepository.save(project);
        } catch (Exception e) {
            log.error("Error creating project: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating project: " + e.getMessage());
        }
    }

    @Override
    public void deleteProject(String userId, Long projectId) {
        UUID userUUID = UUID.fromString(userId);
        projectRepository.findById(projectId).ifPresentOrElse(project -> {
            validateUserAccessToProject(userUUID, project);
            projectRepository.delete(project);
            log.info("Deleted project with id={}", projectId);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found.");
        });
    }

    @Override
    public void deleteSecurityRequirementById(String userId, Long projectId, Long securityRequirementId) {
        UUID userUUID = UUID.fromString(userId);
        Project project = getProjectIfExists(projectId);
        validateUserAccessToProject(userUUID, project);

        project.getProjectSecurityRequirements().removeIf(req -> req.getId().equals(securityRequirementId));
        projectRepository.save(project);
        log.info("Deleted security requirement with id={} from project id={}", securityRequirementId, projectId);
    }

    @Override
    public void createSecurityRequirement(String userId, Long projectId, SecurityRequirementCreationRequest securityRequirementCreationRequest) {
        Project project = getProjectIfExists(projectId);
        validateUserAccessToProject(UUID.fromString(userId), project);

        ProjectSecurityRequirement securityRequirement = new ProjectSecurityRequirement();
        securityRequirement.setTitle(securityRequirementCreationRequest.title());
        securityRequirement.setDescription(securityRequirementCreationRequest.description());
        securityRequirement.setProject(project);

        project.getProjectSecurityRequirements().add(securityRequirement);
        projectRepository.save(project);
        log.info("Added security requirement to project id={}", projectId);
    }

    @Override
    public SecurityRequirementDTO getSecurityRequirementById(String userId, Long projectId, Long securityRequirementId) {
        Project project = getProjectIfExists(projectId);
        validateUserAccessToProject(UUID.fromString(userId), project);

        return project.getProjectSecurityRequirements().stream()
                .filter(req -> req.getId().equals(securityRequirementId))
                .findFirst()
                .map(projectMapper::mapProjectSecurityRequirementToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Security Requirement not found."));
    }

    @Override
    public void updateSecurityRequirementById(String userId, Long projectId, Long securityRequirementId, SecurityRequirementCreationRequest securityRequirementCreationRequest) {
        Project project = getProjectIfExists(projectId);
        validateUserAccessToProject(UUID.fromString(userId), project);

        project.getProjectSecurityRequirements().stream()
                .filter(req -> req.getId().equals(securityRequirementId))
                .findFirst()
                .ifPresentOrElse(req -> {
                    req.setTitle(securityRequirementCreationRequest.title());
                    req.setDescription(securityRequirementCreationRequest.description());
                    projectRepository.save(project);
                    log.info("Updated security requirement with id={} in project id={}", securityRequirementId, projectId);
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Security Requirement not found.");
                });
    }

    /**
     * Retrieves a project by its ID if it exists.
     *
     * @param projectId the ID of the project.
     * @return the project entity.
     */
    private Project getProjectIfExists(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
    }

    /**
     * Validates if a user has access to a given project.
     *
     * @param userId  the UUID of the user.
     * @param project the project to validate access for.
     */
    private void validateUserAccessToProject(UUID userId, Project project) {
        if (!project.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this project.");
        }
    }
}