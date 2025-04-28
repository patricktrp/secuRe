package de.rwth_aachen.swc.recsec.projects.dto.responses;

/**
 * Represents the details of a security requirement associated with a project.
 *
 * @param id          the unique identifier of the security requirement.
 * @param title       the title of the security requirement.
 * @param description the detailed description of the security requirement.
 */
public record SecurityRequirementDTO(
        Long id,
        String title,
        String description
) {}