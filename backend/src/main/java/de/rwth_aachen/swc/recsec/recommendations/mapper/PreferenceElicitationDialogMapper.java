package de.rwth_aachen.swc.recsec.recommendations.mapper;

import de.rwth_aachen.swc.recsec.recommendations.dto.PreferenceElicitationDialogDTO;
import de.rwth_aachen.swc.recsec.recommendations.model.PreferenceElicitationDialog;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting {@link PreferenceElicitationDialog} entities
 * to {@link PreferenceElicitationDialogDTO} objects.
 *
 * This utility helps in transforming the data model used internally in the application
 * to a format suitable for API responses.
 */
@Component
public class PreferenceElicitationDialogMapper {

    /**
     * Maps a {@link PreferenceElicitationDialog} entity to a {@link PreferenceElicitationDialogDTO}.
     *
     * @param preferenceElicitationDialog the entity to be mapped
     * @return a {@link PreferenceElicitationDialogDTO} containing the mapped data
     */
    public PreferenceElicitationDialogDTO mapPreferenceElicitationDialogToDTO(
            PreferenceElicitationDialog preferenceElicitationDialog) {

        // Perform the mapping by extracting content from the entity
        return new PreferenceElicitationDialogDTO(preferenceElicitationDialog.getContent());
    }
}