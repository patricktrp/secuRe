import { projectProperties } from "@/constants/project-properties";

export type SecurityPatternMinimal = {
  id: number;
  name: string;
  securityControl: string;
};

export type SecurityRequirement = {
  id: number;
  title: string;
  description: string;
};

type ProjectPropertiesConst = typeof projectProperties;

type OptionKeys<T> = T extends { options: infer O } ? keyof O : never;

type ProjectProperties = {
  applicationType:
    | OptionKeys<ProjectPropertiesConst["applicationType"]>[]
    | null;
  userBaseSize: OptionKeys<ProjectPropertiesConst["userBaseSize"]> | null;
  securityStrengthNeed: OptionKeys<
    ProjectPropertiesConst["securityStrengthNeed"]
  > | null;
  typeOfData: OptionKeys<ProjectPropertiesConst["typeOfData"]>[] | null;
  typeOfUsers: OptionKeys<ProjectPropertiesConst["typeOfUsers"]>[] | null;
  complianceRequirements:
    | OptionKeys<ProjectPropertiesConst["complianceRequirements"]>[]
    | null;
};

export type ProjectOverview = {
  id: number;
  name: string;
  createdAt: string;
  updatedAt: string;
};

export type Project = {
  properties: ProjectProperties;
  securityRequirements: SecurityRequirement[];
} & ProjectOverview;

export type ProjectCreationData = {
  name: string;
} & ProjectProperties;

export type Answer = {
  answerKey: string;
  displayText: string;
  nextQuestion: string;
};

export type Message = {
  type: "USER" | "ASSISTANT";
  content: string;
  answers?: Answer[];
};

export enum EffectCategory {
  SECURITY_STRENGTH = "Security Strength",
  AUTHENTICATION_STRENGTH = "Authentication Strength",
  USABILITY = "Usability",
  SCALABILITY = "Scalability",
  RESISTANCE_TO_PHISHING = "Resistance to Phishing",
  CONFIDENTIALITY = "Confidentiality",
  COST = "Cost",
  COMPLIANCE = "Compliance",
  AVAILABILITY = "Availability",
  AUDITABILITY = "Auditability",
  PERFORMANCE = "Performance",
  IMPLEMENTATION_COMPLEXITY = "Implementation Complexity",
  MAINTENANCE_COMPLEXITY = "Maintenance Complexity",
  RECOVERY_COMPLEXITY = "Recovery Complexity",
  DEVICE_DEPENDENCY = "Device Dependency",
  THIRD_PARTY_DEPENDENCY = "Third-Party Dependency",
  INFRASTRUCTURE_DEPENDENCY = "Infrastructure Dependency",
  SUITABILITY_FOR_MFA = "Suitability for MFA",
}

export const SECURITY_CONTROLS = [
  {
    id: 1,
    type: "Authentication",
  },
] as const;

export type SecurityControl = (typeof SECURITY_CONTROLS)[number];

export type SecurityPattern = {
  id: number;
  name: string;
  description: SecurityPatternDescription;
  properties: object;
  securityControl: SecurityControl;
};

type Consequence = {
  factor: string;
  reason: string;
};

type Participant = {
  name: string;
  description: string;
};

type SecurityPatternDescription = {
  intent: string;
  problem: string;
  solution: string;
  consequences: {
    positive: Consequence[];
    negative: Consequence[];
  };
  participants: {
    human: Participant[];
    nonHuman: Participant[];
  };
};

export type SecurityPatternRecommendation = {
  securityPattern: SecurityPattern;
  score: number;
  explanations: string[];
};
