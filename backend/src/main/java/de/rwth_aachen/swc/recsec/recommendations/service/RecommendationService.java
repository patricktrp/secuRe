package de.rwth_aachen.swc.recsec.recommendations.service;

import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectDTO;
import de.rwth_aachen.swc.recsec.projects.service.IProjectService;
import de.rwth_aachen.swc.recsec.recommendations.core.ConstraintBasedRecommender;
import de.rwth_aachen.swc.recsec.recommendations.core.dto.SecurityPatternRecommendation;
import de.rwth_aachen.swc.recsec.recommendations.dto.PreferenceElicitationDialogDTO;
import de.rwth_aachen.swc.recsec.recommendations.dto.SecurityPatternRecommendationRequest;
import de.rwth_aachen.swc.recsec.recommendations.mapper.PreferenceElicitationDialogMapper;
import de.rwth_aachen.swc.recsec.recommendations.repository.PreferenceElicitationDialogRepository;
import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;
import de.rwth_aachen.swc.recsec.security_patterns.service.ISecurityPatternService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Service implementation for handling recommendation-related operations.
 *
 * This service provides methods for generating security pattern recommendations
 * and fetching preference elicitation dialogs based on user inputs and preferences.
 */
@Service
@Slf4j
public class RecommendationService implements IRecommendationService {

    private final ISecurityPatternService securityPatternService;
    private final IProjectService projectService;
    private final ConstraintBasedRecommender recommender;
    private final PreferenceElicitationDialogMapper preferenceElicitationDialogMapper;
    private final PreferenceElicitationDialogRepository preferenceElicitationDialogRepository;

    /**
     * Constructs a new RecommendationService with the specified dependencies.
     *
     * @param securityPatternService the service for managing security patterns
     * @param projectService the service for managing project information
     * @param recommender the constraint-based recommender for generating recommendations
     * @param preferenceElicitationDialogMapper the mapper for converting dialog entities to DTOs
     * @param preferenceElicitationDialogRepository the repository for managing dialog entities
     */
    public RecommendationService(
            ISecurityPatternService securityPatternService,
            IProjectService projectService,
            ConstraintBasedRecommender recommender,
            PreferenceElicitationDialogMapper preferenceElicitationDialogMapper,
            PreferenceElicitationDialogRepository preferenceElicitationDialogRepository) {
        this.securityPatternService = securityPatternService;
        this.projectService = projectService;
        this.recommender = recommender;
        this.preferenceElicitationDialogMapper = preferenceElicitationDialogMapper;
        this.preferenceElicitationDialogRepository = preferenceElicitationDialogRepository;
    }

    /**
     * Generates a list of security pattern recommendations based on user preferences and project context.
     *
     * @param userId the ID of the user requesting the recommendations
     * @param securityPatternRecommendationRequest the request containing user preferences and context
     * @return a list of {@link SecurityPatternRecommendation} objects
     */
    @Override
    public List<SecurityPatternRecommendation> getSecurityPatternRecommendations(
            String userId,
            SecurityPatternRecommendationRequest securityPatternRecommendationRequest) {

        log.info("Generating security pattern recommendations for user: {}", userId);
        log.debug("Request details: {}", securityPatternRecommendationRequest);

        List<SecurityPatternDTO> securityPatterns =
                securityPatternService.getSecurityPatternsBySecurityControlId(
                        securityPatternRecommendationRequest.securityControlId());

        ProjectDTO project = projectService.getProjectById(userId, securityPatternRecommendationRequest.projectId());

        return recommender.getSecurityPatternRecommendations(
                securityPatternRecommendationRequest.securityControlId(),
                securityPatterns,
                project,
                securityPatternRecommendationRequest.userPreferences());
    }

    /**
     * Fetches a preference elicitation dialog by the associated security control ID.
     *
     * @param securityControlId the ID of the security control
     * @return a {@link PreferenceElicitationDialogDTO} containing the dialog details
     * @throws ResponseStatusException if no dialog is found for the given security control ID
     */
    @Override
    public PreferenceElicitationDialogDTO getPreferenceElicitationDialogBySecurityControlId(Long securityControlId) {
        log.info("Fetching preference elicitation dialog for security control ID: {}", securityControlId);

        return preferenceElicitationDialogMapper.mapPreferenceElicitationDialogToDTO(
                preferenceElicitationDialogRepository.findBySecurityControlId(securityControlId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Preference Elicitation Dialog not found for Security Control ID: " + securityControlId
                        ))
        );
    }

    /**
     * Fetches a preference elicitation dialog by the associated security pattern ID.
     *
     * @param securityPatternId the ID of the security pattern
     * @return a {@link PreferenceElicitationDialogDTO} containing the dialog details
     * @throws ResponseStatusException if no dialog is found for the given security pattern ID
     */
    @Override
    public PreferenceElicitationDialogDTO getPreferenceElicitationDialogBySecurityPatternId(Long securityPatternId) {
        log.info("Fetching preference elicitation dialog for security pattern ID: {}", securityPatternId);

        return preferenceElicitationDialogMapper.mapPreferenceElicitationDialogToDTO(
                preferenceElicitationDialogRepository.findBySecurityPatternId(securityPatternId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Preference Elicitation Dialog not found for Security Pattern ID: " + securityPatternId
                        ))
        );
    }
}