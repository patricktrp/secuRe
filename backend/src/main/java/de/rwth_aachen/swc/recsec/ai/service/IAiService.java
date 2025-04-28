package de.rwth_aachen.swc.recsec.ai.service;

import de.rwth_aachen.swc.recsec.ai.dto.ChatCompletionRequest;
import de.rwth_aachen.swc.recsec.recommendations.core.dto.Explanation;
import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;

import java.util.List;

public interface IAiService {
    String getChatCompletion(String userId, ChatCompletionRequest chatCompletionRequest);
    String getNaturalLanguageExplanation(SecurityPatternDTO securityPattern, List<Explanation> explanations);
}
