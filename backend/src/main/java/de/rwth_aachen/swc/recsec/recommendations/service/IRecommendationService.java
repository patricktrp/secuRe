package de.rwth_aachen.swc.recsec.recommendations.service;

import de.rwth_aachen.swc.recsec.recommendations.core.dto.SecurityPatternRecommendation;
import de.rwth_aachen.swc.recsec.recommendations.dto.PreferenceElicitationDialogDTO;
import de.rwth_aachen.swc.recsec.recommendations.dto.SecurityPatternRecommendationRequest;

import java.util.List;

/**
 * Service interface for handling recommendation-related operations.
 *
 * This interface defines methods for generating security pattern recommendations
 * and fetching preference elicitation dialogs based on user inputs or related security controls and patterns.
 */
public interface IRecommendationService {

    /**
     * Generates a list of security pattern recommendations based on user preferences and project context.
     *
     * @param userId the ID of the user requesting the recommendations
     * @param securityPatternRecommendationRequest the request containing user preferences and context
     * @return a list of {@link SecurityPatternRecommendation} objects
     */
    List<SecurityPatternRecommendation> getSecurityPatternRecommendations(
            String userId,
            SecurityPatternRecommendationRequest securityPatternRecommendationRequest
    );

    /**
     * Fetches a preference elicitation dialog associated with a specific security control.
     *
     * @param securityControlId the ID of the security control
     * @return a {@link PreferenceElicitationDialogDTO} containing the dialog details
     */
    PreferenceElicitationDialogDTO getPreferenceElicitationDialogBySecurityControlId(Long securityControlId);

    /**
     * Fetches a preference elicitation dialog associated with a specific security pattern.
     *
     * @param securityPatternId the ID of the security pattern
     * @return a {@link PreferenceElicitationDialogDTO} containing the dialog details
     */
    PreferenceElicitationDialogDTO getPreferenceElicitationDialogBySecurityPatternId(Long securityPatternId);
}