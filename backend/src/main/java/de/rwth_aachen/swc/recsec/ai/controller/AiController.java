package de.rwth_aachen.swc.recsec.ai.controller;

import de.rwth_aachen.swc.recsec.ai.dto.ChatCompletionRequest;
import de.rwth_aachen.swc.recsec.ai.dto.NaturalLanguageExplanationRequest;
import de.rwth_aachen.swc.recsec.ai.service.IAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller for AI-related endpoints.
 *
 * Provides APIs for chat completion and natural language explanations
 * using the services of {@link IAiService}.
 */
@RestController
@RequestMapping("/ai")
@Slf4j
@Tag(name = "AI Controller", description = "APIs for AI operations, including chat completions and natural language explanations.")
public class AiController {

    private final IAiService aiService;

    /**
     * Constructs a new AiController with the specified AI service.
     *
     * @param aiService the service to handle AI operations
     */
    public AiController(IAiService aiService) {
        this.aiService = aiService;
    }

    /**
     * Handles requests to generate chat completions based on user input.
     *
     * @param principal the security principal of the authenticated user
     * @param chatCompletionRequest the request containing user input and context for chat completion
     * @return the generated chat completion as a String
     */
    @PostMapping("/chat-completion")
    @Operation(summary = "Generate Chat Completion", description = "Generates a chat response based on user input and context.")
    public String getChatCompletion(Principal principal, @RequestBody ChatCompletionRequest chatCompletionRequest) {
        log.info("Received chat completion request from user: {}", principal.getName());
        return aiService.getChatCompletion(principal.getName(), chatCompletionRequest);
    }

    /**
     * Handles requests to provide natural language explanations for security patterns.
     *
     * @param naturalLanguageExplanationRequest the request containing the security pattern and additional explanations
     * @return the natural language explanation as a String
     */
    @PostMapping(value = "/natural-language-explanation")
    @Operation(summary = "Generate Natural Language Explanation", description = "Generates a natural language explanation for a security pattern recommendation.")
    public String getNaturalLanguageExplanation(@RequestBody NaturalLanguageExplanationRequest naturalLanguageExplanationRequest) {
        log.info("Received natural language explanation request for security pattern: {}", naturalLanguageExplanationRequest.securityPatternDTO());
        return aiService.getNaturalLanguageExplanation(
                naturalLanguageExplanationRequest.securityPatternDTO(),
                naturalLanguageExplanationRequest.explanations()
        );
    }
}