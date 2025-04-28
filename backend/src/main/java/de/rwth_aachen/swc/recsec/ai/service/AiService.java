package de.rwth_aachen.swc.recsec.ai.service;

import de.rwth_aachen.swc.recsec.ai.dto.ChatCompletionRequest;
import de.rwth_aachen.swc.recsec.ai.dto.MessageDTO;
import de.rwth_aachen.swc.recsec.projects.dto.responses.ProjectDTO;
import de.rwth_aachen.swc.recsec.projects.service.IProjectService;
import de.rwth_aachen.swc.recsec.recommendations.core.dto.Explanation;
import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service implementation for AI-related operations.
 *
 * Provides functionalities for generating chat completions and natural language
 * explanations for security patterns using a chat-based AI client.
 */
@Service
@Slf4j
public class AiService implements IAiService {

    private final ChatClient chatModel;
    private final IProjectService projectService;

    @Value("classpath:/prompts/natural-language-explanation.st")
    private Resource naturalLanguageExplanationTemplate;

    /**
     * Constructs a new AiService with the specified dependencies.
     *
     * @param chatClient the ChatClient.Builder used to create the chat model instance
     * @param projectService the project service for fetching project-related information
     */
    public AiService(ChatClient.Builder chatClient, IProjectService projectService) {
        this.chatModel = chatClient.build();
        this.projectService = projectService;
    }

    /**
     * Maps a generic MessageDTO to a specific type of message understood by the chat model.
     *
     * @param message the abstract message DTO
     * @return a specific instance of {@link Message} based on the message type
     * @throws IllegalArgumentException if the message type is unknown
     */
    private Message mapAbstractToSpecificMessage(MessageDTO message) {
        switch (message.type()) {
            case MessageType.SYSTEM:
                return new SystemMessage(message.content());
            case MessageType.USER:
                return new UserMessage(message.content());
            case MessageType.ASSISTANT:
                return new AssistantMessage(message.content());
            default:
                throw new IllegalArgumentException("Unknown message type: " + message.type());
        }
    }

    /**
     * Generates a chat completion based on user input and project-specific context.
     *
     * @param userId the ID of the user initiating the request
     * @param chatCompletionRequest the request containing messages and project ID
     * @return the generated chat completion as a String
     */
    @Override
    public String getChatCompletion(String userId, ChatCompletionRequest chatCompletionRequest) {
        log.info("Generating chat completion for user: {}", userId);

        // Map incoming messages to specific message types
        List<Message> messages = chatCompletionRequest.messages().stream()
                .map(this::mapAbstractToSpecificMessage)
                .collect(Collectors.toList());

        // Fetch project information for context
        ProjectDTO project = projectService.getProjectById(userId, chatCompletionRequest.projectId());

        // Add a system message providing instructions and project details
        messages.addFirst(new SystemMessage("You are a useful assistant. Please answer questions regarding software architecture and security. " +
                "Answer concisely and avoid asking your own questions! " +
                "Use the following information about the user project to provide useful answers: Project ID: " + project.id()));

        // Generate and return the chat completion
        return chatModel.prompt(new Prompt(messages)).call().content();
    }

    /**
     * Generates a natural language explanation for a given security pattern and its constraints.
     *
     * @param securityPattern the security pattern to explain
     * @param explanations a list of constraints or explanations to include
     * @return the generated natural language explanation as a String
     */
    @Override
    public String getNaturalLanguageExplanation(SecurityPatternDTO securityPattern, List<Explanation> explanations) {
        log.info("Generating natural language explanation for security pattern: {}", securityPattern);

        // Create a prompt template using the predefined template file
        PromptTemplate promptTemplate = new PromptTemplate(naturalLanguageExplanationTemplate);

        // Populate the template with input data
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("securityPattern", securityPattern);
        templateData.put("constraints", explanations);

        // Generate and return the explanation using the template
        Prompt prompt = promptTemplate.create(templateData);
        return chatModel.prompt(prompt).call().content();
    }
}