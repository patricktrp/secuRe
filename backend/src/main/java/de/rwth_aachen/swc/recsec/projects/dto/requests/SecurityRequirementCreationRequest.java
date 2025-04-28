package de.rwth_aachen.swc.recsec.projects.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a request to create a new security requirement for a project.
 * Contains the necessary information, such as title and description, for defining a security requirement.
 *
 * @param title       the title of the security requirement. Must not be null or empty, with a maximum length of 255 characters.
 * @param description the detailed description of the security requirement.
 */
public record SecurityRequirementCreationRequest(
        @NotNull @Size(max = 255) String title,
        String description
) {}