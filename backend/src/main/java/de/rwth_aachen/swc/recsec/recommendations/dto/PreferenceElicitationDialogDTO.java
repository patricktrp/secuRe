package de.rwth_aachen.swc.recsec.recommendations.dto;

import java.util.Map;

/**
 * Data Transfer Object (DTO) representing a preference elicitation dialog.
 *
 * This record is used to encapsulate the details of a preference elicitation dialog
 * in a flexible structure, allowing dynamic attributes through a key-value mapping.
 *
 * @param preferenceElicitationDialog a map containing the dialog details,
 *                                     where keys are property names and values are the corresponding data
 */
public record PreferenceElicitationDialogDTO(
        Map<String, Object> preferenceElicitationDialog
) {}