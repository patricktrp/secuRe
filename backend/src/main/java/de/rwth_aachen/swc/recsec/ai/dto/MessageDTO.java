package de.rwth_aachen.swc.recsec.ai.dto;

import org.springframework.ai.chat.messages.MessageType;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a message in the chat system.
 *
 * This record encapsulates the type, content, and potential answers for a message,
 * making it suitable for use in AI-driven chat interactions.
 *
 * @param type the type of the message (e.g., SYSTEM, USER, ASSISTANT)
 * @param content the textual content of the message
 * @param answers a list of possible answers or options associated with the message
 */
public record MessageDTO(
        MessageType type,
        String content,
        List<AnswerDTO> answers
) {
}