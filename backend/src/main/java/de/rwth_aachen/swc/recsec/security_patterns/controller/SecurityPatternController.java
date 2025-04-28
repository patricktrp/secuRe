package de.rwth_aachen.swc.recsec.security_patterns.controller;

import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;
import de.rwth_aachen.swc.recsec.security_patterns.service.ISecurityPatternService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing security patterns.
 * Provides endpoints for retrieving security patterns and their details.
 */
@RestController
@RequestMapping("/security-patterns")
@Slf4j
@Tag(name = "Security Patterns", description = "Endpoints for managing security patterns")
public class SecurityPatternController {

    private final ISecurityPatternService securityPatternService;

    /**
     * Constructor for SecurityPatternController.
     *
     * @param securityPatternService the service handling security pattern-related operations
     */
    public SecurityPatternController(ISecurityPatternService securityPatternService) {
        this.securityPatternService = securityPatternService;
    }

    /**
     * Retrieves all available security patterns.
     *
     * @return a list of {@link SecurityPatternDTO}
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all security patterns", description = "Fetches a list of all available security patterns")
    public List<SecurityPatternDTO> getSecurityPatterns() {
        log.info("Fetching all security patterns");
        return securityPatternService.getSecurityPatterns();
    }

    /**
     * Retrieves detailed information about a specific security pattern by its ID.
     *
     * @param securityPatternId the ID of the security pattern to retrieve
     * @return a {@link SecurityPatternDTO}
     */
    @GetMapping("/{securityPatternId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a security pattern by ID", description = "Fetches detailed information about a specific security pattern by its unique ID")
    public SecurityPatternDTO getSecurityPatternById(
            @Parameter(description = "The ID of the security pattern to retrieve", required = true)
            @PathVariable Long securityPatternId
    ) {
        log.info("Fetching security pattern with id={}", securityPatternId);
        return securityPatternService.getSecurityPatternById(securityPatternId);
    }
}