package de.rwth_aachen.swc.recsec.recommendations.core.constraints;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component for loading and mapping constraints from the repository.
 *
 * The {@code ConstraintLoader} retrieves constraint entities from the database
 * and converts them into {@link SpelConstraint} objects for use in the recommendation system.
 */
@Component
@Slf4j
public class ConstraintLoader {

    private final ConstraintRepository constraintRepository;

    /**
     * Constructs a new {@code ConstraintLoader} with the given repository.
     *
     * @param constraintRepository the repository used to retrieve constraint entities
     */
    public ConstraintLoader(ConstraintRepository constraintRepository) {
        this.constraintRepository = constraintRepository;
    }

    /**
     * Loads and maps constraints associated with a specific security control ID.
     *
     * @param securityControlId the ID of the security control
     * @return a list of {@link SpelConstraint} objects
     */
    public List<SpelConstraint> loadConstraintsBySecurityControlId(Long securityControlId) {
        log.info("Loading constraints for security control ID: {}", securityControlId);

        List<ConstraintEntity> entities = constraintRepository.findBySecurityControlId(securityControlId);

        return entities.stream()
                .map(this::mapConstraintEntityToSpelConstraint)
                .toList();
    }

    /**
     * Loads and maps constraints associated with a specific security pattern ID.
     *
     * @param securityPatternId the ID of the security pattern
     * @return a list of {@link SpelConstraint} objects
     */
    public List<SpelConstraint> loadConstraintsBySecurityPatternId(Long securityPatternId) {
        log.info("Loading constraints for security pattern ID: {}", securityPatternId);

        List<ConstraintEntity> entities = constraintRepository.findBySecurityControlId(securityPatternId);

        return entities.stream()
                .map(this::mapConstraintEntityToSpelConstraint)
                .toList();
    }

    /**
     * Maps a {@link ConstraintEntity} to a {@link SpelConstraint}.
     *
     * @param entity the {@code ConstraintEntity} to map
     * @return a {@code SpelConstraint} object
     */
    private SpelConstraint mapConstraintEntityToSpelConstraint(ConstraintEntity entity) {
        log.debug("Mapping ConstraintEntity to SpelConstraint: {}", entity);

        return new SpelConstraint(
                entity.getApplicabilityExpression(),
                entity.getSatisfactionExpression(),
                entity.getSatisfiedExplanationExpression(),
                entity.getViolatedExplanationExpression(),
                entity.getWeight(),
                entity.getIsHard(),
                entity.getName(),
                entity.getDescription()
        );
    }
}