package de.rwth_aachen.swc.recsec.ai.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a request for generating chat completions.
 *
 * This record encapsulates the project context and a list of messages that form the input
 * for generating AI-based chat responses.
 *
 * @param projectId the ID of the project for which the chat completion is requested
 * @param messages a list of {@link MessageDTO} objects representing the conversation context
 */
public record ChatCompletionRequest(
        Long projectId,
        List<MessageDTO> messages
) { }