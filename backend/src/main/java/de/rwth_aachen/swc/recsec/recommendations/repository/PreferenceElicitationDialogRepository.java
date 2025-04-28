package de.rwth_aachen.swc.recsec.recommendations.repository;

import de.rwth_aachen.swc.recsec.recommendations.model.PreferenceElicitationDialog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link PreferenceElicitationDialog} entities.
 *
 * This interface provides methods to perform CRUD operations and query preference elicitation
 * dialogs based on related security control or security pattern IDs.
 */
public interface PreferenceElicitationDialogRepository extends JpaRepository<PreferenceElicitationDialog, Long> {

    /**
     * Finds a preference elicitation dialog by its associated security control ID.
     *
     * @param securityControlId the ID of the security control
     * @return an {@link Optional} containing the found {@link PreferenceElicitationDialog}, or empty if none is found
     */
    Optional<PreferenceElicitationDialog> findBySecurityControlId(Long securityControlId);

    /**
     * Finds a preference elicitation dialog by its associated security pattern ID.
     *
     * @param securityPatternId the ID of the security pattern
     * @return an {@link Optional} containing the found {@link PreferenceElicitationDialog}, or empty if none is found
     */
    Optional<PreferenceElicitationDialog> findBySecurityPatternId(Long securityPatternId);
}