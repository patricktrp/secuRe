import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

const patternProperty: Record<string, string> = {
  cost: "Cost",
  usability: "Usability",
  compliance: "Compliance",
  performance: "Performance",
  scalability: "Scalability",
  auditability: "Auditability",
  availability: "Availability",
  confidentiality: "Confidentiality",
  mfa_suitability: "MFA Suitability",
  device_dependency: "Device Dependency",
  security_strength: "Security Strength",
  recovery_complexity: "Recovery Complexity",
  maintenance_complexity: "Maintenance Complexity",
  resistance_to_phishing: "Resistance to Phishing",
  third_party_dependency: "Third-Party Dependency",
  authentication_strength: "Authentication Strength",
  implementation_complexity: "Implementation Complexity",
  infrastructure_dependency: "Infrastructure Dependency",
};

export const mapProperty = (propertyKey: string) => {
  return patternProperty[propertyKey] || propertyKey;
};
