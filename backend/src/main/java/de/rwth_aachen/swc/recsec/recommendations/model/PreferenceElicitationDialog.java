package de.rwth_aachen.swc.recsec.recommendations.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

/**
 * Entity representing a preference elicitation dialog.
 *
 * This class is mapped to the `preference_elicitation_dialogs` table in the database
 * and stores information about dialogs, including their content and related security controls or patterns.
 */
@Getter
@Setter
@Entity
@Table(name = "preference_elicitation_dialogs")
public class PreferenceElicitationDialog {

    /**
     * The unique identifier for the preference elicitation dialog.
     * This is the primary key and is generated using a sequence.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preference_elicitation_dialogs_id_gen")
    @SequenceGenerator(
            name = "preference_elicitation_dialogs_id_gen",
            sequenceName = "preference_elicitation_dialogs_id_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The content of the dialog, stored as a JSON structure.
     * This field is mapped to a JSON column in the database.
     */
    @Column(name = "content")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> content;

    /**
     * The ID of the related security control, if applicable.
     */
    @Column(name = "security_control_id")
    private Long securityControlId;

    /**
     * The ID of the related security pattern, if applicable.
     */
    @Column(name = "security_pattern_id")
    private Long securityPatternId;
}