import { getProjectById, getProjects } from "@/services/api/projects";
import {
  Project,
  ProjectOverview,
  SECURITY_CONTROLS,
  SecurityControl,
  SecurityRequirement,
} from "@/types/types";
import { useEffect, useState } from "react";

/**
 * Custom hook to fetch projects and their details
 */
export const useProjects = (start:boolean) => {
  const [projects, setProjects] = useState<ProjectOverview[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [selectedProject, setSelectedProject] = useState<Project | null>(null);
  const [selectedSecurityRequirement, setSelectedSecurityRequirement] =
    useState<SecurityRequirement | null>(null);
  const [selectedSecurityControl, setSelectedSecurityControl] =
    useState<SecurityControl | null>(null);

  useEffect(() => {
    const fetchProjects = async () => {
      const data = await getProjects();
      setProjects(data);
      setIsLoading(false);
    };

    fetchProjects();
  }, [start]);

  const selectProject = async (id: ProjectOverview["id"]) => {
    if (!projects.find((project) => project.id === id)) {
      throw new Error("Project not found");
    }
    setIsLoading(true);
    const projectDetails = await getProjectById(id);
    setSelectedProject(projectDetails);
    setIsLoading(false);
  };

  const selectSecurityRequirement = (securityRequirementId: number) => {
    if (!selectedProject) {
      throw new Error("a Project needs to be selected first");
    }

    const securityRequirement = selectedProject?.securityRequirements.find(
      (sr) => sr.id === securityRequirementId
    );
    if (!securityRequirement) {
      throw new Error("Security requirement not found");
    }
    setSelectedSecurityRequirement(securityRequirement);
  };

  const selectSecurityControl = (securityControlId: SecurityControl["id"]) => {
    const securityControl = SECURITY_CONTROLS.find(
      (sc) => sc.id === securityControlId
    );
    if (!securityControl) {
      throw new Error("The is no security Control with this number");
    }

    setSelectedSecurityControl(securityControl);
  };

  return {
    projects,
    selectedProject,
    selectedSecurityRequirement,
    selectedSecurityControl,
    isLoading,
    selectProject,
    selectSecurityRequirement,
    selectSecurityControl,
  };
};
