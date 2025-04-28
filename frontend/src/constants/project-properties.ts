import {
  BookOpenCheck,
  Brain,
  Building2,
  Cloud,
  Cpu,
  Globe,
  HeartPulse,
  Landmark,
  Monitor,
  Server,
  Smartphone,
  Tally1,
  Tally2,
  Tally3,
  Tally4,
  User,
  UserCog,
  UserRoundPlus,
} from "lucide-react";

/**
 * These are the possible Properties an Project can have. The backend does not expect fixed ones, therefore this is the source of truth.
 * Make sure to always add options property as we infer types from them (see types.ts)
 */
export const projectProperties = {
  applicationType: {
    metadata: {
      displayName: "Application Type",
      type: "single-select",
    },
    options: {
      WEB_APP: { displayText: "Web application", icon: Globe },
      MOBILE_APP: { displayText: "Mobile application", icon: Smartphone },
      DESKTOP_APP: { displayText: "Desktop application", icon: Monitor },
      CLOUD_APP: { displayText: "Cloud-based application", icon: Cloud },
      EMBEDDED_IOT: { displayText: "Embedded System / IoT", icon: Cpu },
      API_MICROSERVICES: { displayText: "API or microservices", icon: Server },
    },
  },
  userBaseSize: {
    metadata: {
      displayName: "Userbase Size",
      type: "single-select",
    },
    options: {
      SMALL: { displayText: "Small (< 1.000)", icon: Tally1 },
      MEDIUM: { displayText: "Medium (1.000 - 10.000)", icon: Tally2 },
      LARGE: { displayText: "Large (10.000 - 100.000)", icon: Tally3 },
      ENTERPRISE: { displayText: "Enterprise (> 100.000)", icon: Tally4 },
    },
  },
  securityStrengthNeed: {
    metadata: {
      displayName: "Security Strength Needed",
      type: "single-select",
    },
    options: {
      LOW: { displayText: "Low (e.g., public blogs, forums)", icon: Tally1 },
      MEDIUM: {
        displayText: "Medium (business apps, personal data)",
        icon: Tally2,
      },
      HIGH: {
        displayText: "High (e.g., financial/healthcare apps)",
        icon: Tally3,
      },
      VERY_HIGH: {
        displayText: "Very High (e.g., government systems)",
        icon: Tally4,
      },
    },
  },
  typeOfData: {
    metadata: {
      displayName: "Type of Data",
      type: "multi-select",
    },
    options: {
      PII: {
        displayText: "Personally identifiable information (PII)",
        icon: User,
      },
      FINANCIAL: { displayText: "Financial data", icon: Landmark },
      HEALTH: { displayText: "Health data", icon: HeartPulse },
      IP_PROPRIETARY: {
        displayText: "Intellectual property or proprietary data",
        icon: Brain,
      },
      PUBLIC: { displayText: "Publicly available data", icon: BookOpenCheck },
    },
  },
  typeOfUsers: {
    metadata: {
      displayName: "Type of Users",
      type: "multi-select",
    },
    options: {
      PUBLIC: {
        displayText: "General Public (e.g., customers, patients)",
        icon: User,
      },
      INTERNAL: {
        displayText: "Internal Staff (e.g., employees)",
        icon: Building2,
      },
      PRIVILEGED: {
        displayText: "Privileged Users (e.g. system administrators)",
        icon: UserRoundPlus,
      },
      AUTOMATED: {
        displayText: "Automated Systems (e.g., APIs, IoT devices)",
        icon: UserCog,
      },
    },
  },
  complianceRequirements: {
    metadata: {
      displayName: "Compliance Requirements",
      type: "multi-select",
    },
    options: {
      GDPR: { displayText: "GDPR" },
      HIPAA: { displayText: "HIPAA" },
      PCI_DSS: { displayText: "PCI-DSS" },
      ISO_27001: { displayText: "ISO 27001" },
    },
  },
};
