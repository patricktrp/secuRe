package de.rwth_aachen.swc.recsec.security_patterns.repository;

import de.rwth_aachen.swc.recsec.security_patterns.model.SecurityPattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link SecurityPattern} entities.
 *
 * This interface extends {@link JpaRepository}, providing CRUD operations
 * and additional custom methods for accessing SecurityPattern data.
 */
public interface SecurityPatternRepository extends JpaRepository<SecurityPattern, Long> {

    /**
     * Finds all {@link SecurityPattern} entities associated with a given security control ID.
     *
     * @param securityControlId the ID of the security control to filter patterns by
     * @return a list of {@link SecurityPattern} entities associated with the specified security control ID
     */
    List<SecurityPattern> findAllBySecurityControl_Id(Long securityControlId);
}