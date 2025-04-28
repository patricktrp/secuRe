package de.rwth_aachen.swc.recsec.security_patterns.service;

import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;
import de.rwth_aachen.swc.recsec.security_patterns.mapper.SecurityPatternMapper;
import de.rwth_aachen.swc.recsec.security_patterns.model.SecurityPattern;
import de.rwth_aachen.swc.recsec.security_patterns.repository.SecurityPatternRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing security patterns.
 *
 * This class provides methods to perform CRUD operations for Security Patterns.
 * It interacts with the repository layer to fetch and save data and uses
 * a mapper for transforming entity objects to DTOs.
 */
@Service
@Slf4j
public class SecurityPatternService implements ISecurityPatternService {

    private final SecurityPatternRepository securityPatternRepository;
    private final SecurityPatternMapper securityPatternMapper;

    /**
     * Constructor for SecurityPatternService.
     *
     * @param securityPatternRepository Repository for performing database operations on Security Patterns.
     * @param securityPatternMapper Mapper for transforming SecurityPattern entities to DTOs and vice versa.
     */
    public SecurityPatternService(SecurityPatternRepository securityPatternRepository, SecurityPatternMapper securityPatternMapper) {
        this.securityPatternRepository = securityPatternRepository;
        this.securityPatternMapper = securityPatternMapper;
    }

    /**
     * Retrieves all available security patterns.
     *
     * @return A list of SecurityPatternDTO objects representing all security patterns.
     */
    @Override
    @Cacheable(value = "allSecurityPatternsCache")
    public List<SecurityPatternDTO> getSecurityPatterns() {
        log.info("Fetching all security patterns.");
        List<SecurityPattern> securityPatterns = securityPatternRepository.findAll();
        return securityPatterns.stream()
                .map(securityPatternMapper::mapSecurityPatternToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves security patterns associated with a specific security control ID.
     *
     * @param securityControlId The ID of the security control to filter patterns by.
     * @return A list of SecurityPatternDTO objects associated with the given security control ID.
     */
    @Override
    public List<SecurityPatternDTO> getSecurityPatternsBySecurityControlId(Long securityControlId) {
        log.info("Fetching security patterns for security control ID: {}", securityControlId);
        return securityPatternRepository.findAllBySecurityControl_Id(securityControlId).stream()
                .map(securityPatternMapper::mapSecurityPatternToDTO)
                .toList();
    }

    /**
     * Retrieves a specific security pattern by its unique ID.
     *
     * @param securityPatternId The unique identifier of the security pattern.
     * @return A SecurityPatternDTO object representing the security pattern.
     * @throws IllegalArgumentException if the security pattern with the given ID does not exist.
     */
    @Override
    @Cacheable(value = "securityPatternsCache", key = "#securityPatternId")
    public SecurityPatternDTO getSecurityPatternById(Long securityPatternId) {
        log.info("Fetching security pattern with ID: {}", securityPatternId);
        SecurityPattern securityPattern = securityPatternRepository.findById(securityPatternId)
                .orElseThrow(() -> {
                    log.error("Security pattern not found for ID: {}", securityPatternId);
                    return new IllegalArgumentException("Security pattern not found for ID: " + securityPatternId);
                });
        return securityPatternMapper.mapSecurityPatternToDTO(securityPattern);
    }
}