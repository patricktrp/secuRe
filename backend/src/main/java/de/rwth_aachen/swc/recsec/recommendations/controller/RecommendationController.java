package de.rwth_aachen.swc.recsec.recommendations.controller;

import de.rwth_aachen.swc.recsec.recommendations.core.dto.SecurityPatternRecommendation;
import de.rwth_aachen.swc.recsec.recommendations.dto.PreferenceElicitationDialogDTO;
import de.rwth_aachen.swc.recsec.recommendations.dto.SecurityPatternRecommendationRequest;
import de.rwth_aachen.swc.recsec.recommendations.service.IRecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

/**
 * Controller for handling recommendation-related endpoints.
 *
 * This controller provides APIs for security pattern recommendations and preference elicitation dialogs
 * based on user inputs and preferences.
 */
@RestController
@RequestMapping("/recommendations")
@Slf4j
public class RecommendationController {

    private final IRecommendationService recommendationService;

    /**
     * Constructs a new RecommendationController with the specified recommendation service.
     *
     * @param recommendationService the service to handle recommendation operations
     */
    public RecommendationController(IRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Handles POST requests to generate security pattern recommendations.
     *
     * @param principal the authenticated user making the request
     * @param securityPatternRecommendationRequest the request containing user preferences and context
     * @return a list of {@link SecurityPatternRecommendation} objects
     */
    @PostMapping("/security-patterns")
    public List<SecurityPatternRecommendation> getSecurityPatternRecommendations(
            Principal principal,
            @RequestBody SecurityPatternRecommendationRequest securityPatternRecommendationRequest) {

        log.info("Received security pattern recommendation request from user: {}", principal.getName());
        log.debug("User preferences: {}", securityPatternRecommendationRequest.userPreferences());

        List<SecurityPatternRecommendation> recommendations =
                recommendationService.getSecurityPatternRecommendations(principal.getName(), securityPatternRecommendationRequest);

        log.debug("Generated recommendations: {}", recommendations);

        return recommendations;
    }

    /**
     * Handles GET requests to fetch preference elicitation dialogs.
     *
     * Only one parameter (securityControlId or securityPatternId) must be provided.
     *
     * @param securityControlId the ID of the security control (optional)
     * @param securityPatternId the ID of the security pattern (optional)
     * @return a {@link PreferenceElicitationDialogDTO} containing dialog details
     * @throws ResponseStatusException if neither or both parameters are provided
     */
    @GetMapping("/preference-elicitation-dialogs")
    public PreferenceElicitationDialogDTO getPreferenceElicitationDialogs(
            @RequestParam(required = false) Long securityControlId,
            @RequestParam(required = false) Long securityPatternId) {

        if ((securityControlId == null && securityPatternId == null) ||
                (securityControlId != null && securityPatternId != null)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Exactly one parameter (securityControlId or securityPatternId) must be provided."
            );
        }

        PreferenceElicitationDialogDTO preferenceElicitationDialogDTO;

        if (securityControlId != null) {
            log.info("Fetching preference elicitation dialog for security control ID: {}", securityControlId);
            preferenceElicitationDialogDTO = recommendationService.getPreferenceElicitationDialogBySecurityControlId(securityControlId);
        } else {
            log.info("Fetching preference elicitation dialog for security pattern ID: {}", securityPatternId);
            preferenceElicitationDialogDTO = recommendationService.getPreferenceElicitationDialogBySecurityPatternId(securityPatternId);
        }

        return preferenceElicitationDialogDTO;
    }
}