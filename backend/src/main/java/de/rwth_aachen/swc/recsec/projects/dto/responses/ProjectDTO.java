package de.rwth_aachen.swc.recsec.projects.dto.responses;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * Represents the detailed information of a project, including its metadata and associated security requirements.
 *
 * @param id                  the unique identifier of the project.
 * @param name                the name of the project.
 * @param createdAt           the timestamp when the project was created.
 * @param updatedAt           the timestamp when the project was last updated.
 * @param securityRequirements the list of associated security requirements.
 */
public record ProjectDTO(
        Long id,
        String name,
        Map<String, Object> properties,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<SecurityRequirementDTO> securityRequirements
) {}