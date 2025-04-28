package de.rwth_aachen.swc.recsec.projects.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Map;

/**
 * Represents a request to create a new project.
 * Contains the necessary information for creating a project, including its name and properties.
 *
 * @param name       the name of the project. Must not be null or empty, with a maximum length of 255 characters.
 * @param properties a map of key-value pairs representing additional project properties.
 */
public record ProjectCreationRequest(
        @NotNull @Size(max = 255) String name,
        Map<String, Object> properties
) {}