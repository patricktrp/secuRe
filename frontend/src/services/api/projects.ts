import axiosInstance from "./axiosInstance";
import { Project, ProjectOverview } from "@/types/types";
import { ProjectCreationData } from "@/types/types";

const getProjects = async (): Promise<ProjectOverview[]> => {
  try {
    const res = await axiosInstance.get("/projects");
    return res.data;
  } catch (error) {
    console.error("Error fetching projects:", error);
    throw error;
  }
};

const getProjectById = async (projectId: number): Promise<Project> => {
  try {
    const res = await axiosInstance.get(`/projects/${projectId}`);
    return res.data;
  } catch (error) {
    console.error(`Error fetching project with ID (${projectId}):`, error);
    throw error;
  }
};

const createProject = async (projectData: ProjectCreationData) => {
  try {
    const { name, ...properties } = projectData;

    return await axiosInstance.post("/projects", {
      name: name,
      properties: {
        ...properties,
      },
    });
  } catch (error) {
    console.error("Error creating project:", error);
    throw error; // Re-throw for error handling at a higher level
  }
};

const deleteProjectById = async (projectId: number) => {
  try {
    await axiosInstance.delete(`/projects/${projectId}`);
  } catch (error) {
    console.error(`Error deleting project with ID (${projectId}):`, error);
    throw error;
  }
};

export type SecurityRequirementCreationRequest = {
  title: string;
  description: string;
};

const createSecurityRequirement = async (
  projectId: number,
  data: SecurityRequirementCreationRequest
) => {
  try {
    return await axiosInstance.post(
      `/projects/${projectId}/security-requirements`,
      data
    );
  } catch (error) {
    console.error(
      `Error creating security requirement for project ID (${projectId}):`,
      error
    );
    throw error;
  }
};

const deleteSecurityRequirementById = async (
  projectId: number,
  securityRequirementId: number
) => {
  try {
    await axiosInstance.delete(
      `/projects/${projectId}/security-requirements/${securityRequirementId}`
    );
  } catch (error) {
    console.error(
      `Error deleting security requirement with ID (${securityRequirementId}) for project ID (${projectId}):`,
      error
    );
    throw error;
  }
};

export {
  getProjects,
  getProjectById,
  createProject,
  deleteProjectById,
  createSecurityRequirement,
  deleteSecurityRequirementById,
};
