package de.rwth_aachen.swc.recsec.recommendations.core.constraints;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link ConstraintEntity} objects.
 *
 * This interface provides methods for performing CRUD operations and
 * querying constraints by their associated security control or security pattern IDs.
 */
public interface ConstraintRepository extends JpaRepository<ConstraintEntity, Long> {

    /**
     * Finds all constraints associated with a specific security control ID.
     *
     * @param securityControlId the ID of the security control
     * @return a list of {@link ConstraintEntity} objects linked to the given security control ID
     */
    List<ConstraintEntity> findBySecurityControlId(Long securityControlId);

    /**
     * Finds all constraints associated with a specific security pattern ID.
     *
     * @param securityPatternId the ID of the security pattern
     * @return a list of {@link ConstraintEntity} objects linked to the given security pattern ID
     */
    List<ConstraintEntity> findBySecurityPatternId(Long securityPatternId);
}