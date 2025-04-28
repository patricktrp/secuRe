package de.rwth_aachen.swc.recsec.security_patterns.mapper;

import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityControlDTO;
import de.rwth_aachen.swc.recsec.security_patterns.dto.SecurityPatternDTO;
import de.rwth_aachen.swc.recsec.security_patterns.model.SecurityControl;
import de.rwth_aachen.swc.recsec.security_patterns.model.SecurityPattern;
import org.springframework.stereotype.Component;

/**
 * Mapper class for transforming SecurityPattern and SecurityControl entities to their corresponding DTOs.
 *
 * This class provides methods to map entity objects into Data Transfer Objects (DTOs),
 * ensuring a clear separation between database models and the data presented to the client.
 */
@Component
public class SecurityPatternMapper {

    /**
     * Maps a {@link SecurityPattern} entity to a {@link SecurityPatternDTO}.
     *
     * @param securityPattern the SecurityPattern entity to be mapped.
     * @return a SecurityPatternDTO containing the mapped data.
     */
    public SecurityPatternDTO mapSecurityPatternToDTO(SecurityPattern securityPattern) {
        if (securityPattern == null) {
            return null; // Handle null gracefully
        }
        return new SecurityPatternDTO(
                securityPattern.getId(),
                securityPattern.getName(),
                securityPattern.getDescription(),
                securityPattern.getProperties(),
                mapSecurityControlToDTO(securityPattern.getSecurityControl())
        );
    }

    /**
     * Maps a {@link SecurityControl} entity to a {@link SecurityControlDTO}.
     *
     * @param securityControl the SecurityControl entity to be mapped.
     * @return a SecurityControlDTO containing the mapped data.
     */
    private SecurityControlDTO mapSecurityControlToDTO(SecurityControl securityControl) {
        if (securityControl == null) {
            return null; // Handle null gracefully
        }
        return new SecurityControlDTO(
                securityControl.getId(),
                securityControl.getType().getDisplayName()
        );
    }
}