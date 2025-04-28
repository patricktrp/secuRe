package de.rwth_aachen.swc.recsec.projects.dto.responses;

import java.time.OffsetDateTime;

/**
 * Represents an overview of a project, providing essential metadata such as ID, name, and timestamps.
 *
 * @param id        the unique identifier of the project.
 * @param name      the name of the project.
 * @param createdAt the timestamp when the project was created.
 * @param updatedAt the timestamp when the project was last updated.
 */
public record ProjectOverviewDTO(
        Long id,
        String name,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}