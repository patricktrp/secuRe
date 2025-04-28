CREATE TABLE security_controls (
                                   id BIGSERIAL PRIMARY KEY,
                                   type VARCHAR(255) NOT NULL,
                                   description TEXT
);

CREATE TABLE security_patterns (
                                   id BIGSERIAL PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   description JSONB,
                                   properties JSONB,
                                   security_control BIGINT REFERENCES security_controls(id) ON DELETE CASCADE
);

CREATE TABLE security_design_patterns (
                                          id BIGSERIAL PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
                                          description JSONB,
                                          properties JSONB,
                                          security_conceptual_pattern BIGINT references security_patterns(id) ON DELETE CASCADE
);

CREATE TABLE constraints (
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     description TEXT,
     applicability_expression TEXT NOT NULL,
     satisfaction_expression TEXT NOT NULL,
     satisfied_explanation_expression TEXT NOT NULL,
     violated_explanation_expression TEXT NOT NULL,
     weight INT NOT NULL DEFAULT 1,
     is_hard BOOLEAN NOT NULL DEFAULT TRUE,
     created_at TIMESTAMPTZ,
     updated_at TIMESTAMPTZ,
     security_control_id BIGINT,
     security_pattern_id BIGINT,
     CONSTRAINT one_reference_only CHECK (
         (security_control_id IS NOT NULL AND security_pattern_id IS NULL) OR
         (security_control_id IS NULL AND security_pattern_id IS NOT NULL)
         )
);

CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    properties JSONB,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);

CREATE TABLE project_security_requirements (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    project_id BIGINT REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE preference_elicitation_dialogs (
                                                id BIGSERIAL PRIMARY KEY,
                                                content JSONB,
                                                security_control_id BIGINT,
                                                security_pattern_id BIGINT,
                                                CONSTRAINT one_reference_only CHECK (
                                                    (security_control_id IS NOT NULL AND security_pattern_id IS NULL) OR
                                                    (security_control_id IS NULL AND security_pattern_id IS NOT NULL)
                                                    )
)
