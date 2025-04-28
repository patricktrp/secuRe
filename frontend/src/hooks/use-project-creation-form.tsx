import {useReducer } from "react";
import { useQueryClient } from "@tanstack/react-query";
import { useMultiStepForm } from "./use-multistep-form";
import MultiSelect from "@/components/features/projects/project-creation-form/multi-select";
import { createProject } from "@/services/api/projects";
import { useMutation } from "@tanstack/react-query";
import ProjectNameForm from "@/components/features/projects/project-creation-form/project-name-form";
import {
  BookOpenCheck,
  Brain,
  HeartPulse,
  Landmark,
  User,
  Server,
  Globe,
  Smartphone,
  Monitor,
  Cloud,
  Cpu,
  Tally1,
  Tally2,
  Tally3,
  Tally4,
  Building2,
  UserRoundPlus,
  UserCog,
} from "lucide-react";
import SingleSelect from "@/components/features/projects/project-creation-form/single-select";
import { ProjectCreationData } from "@/types/types";

const INITIAL_PROJECT_DATA: ProjectCreationData = {
  name: "",
  typeOfData: null,
  applicationType: null,
  userBaseSize: null,
  securityStrengthNeed: null,
  typeOfUsers: null,
  complianceRequirements: null,
};

type UpdateActions<
  Key extends keyof ProjectCreationData = keyof ProjectCreationData
> = {
  [K in Key]: { type: "update"; key: K; value: ProjectCreationData[K] };
}[Key];

type ProjectDataFormActions = UpdateActions | { type: "reset" };

const reducer = (
  state: ProjectCreationData,
  action: ProjectDataFormActions
): ProjectCreationData => {
  switch (action.type) {
    case "update":
      return {
        ...state,
        [action.key]: action.value, // TypeScript ensures `value` matches the type for `key`
      };
    case "reset":
      return INITIAL_PROJECT_DATA;
    default:
      throw new Error("Unhandled action type");
  }
};

const useProjectCreationForm = () => {
  const queryClient = useQueryClient();

  const [projectData, dispatch] = useReducer(reducer, INITIAL_PROJECT_DATA);

  const resetProjectData = () => {
    goTo(0);
    dispatch({
      type: "reset",
    });
  };

  const { step, isFirstStep, isLastStep, next, back, goTo } = useMultiStepForm([
    {
      description: "What is the name of the project?",
      component: (
        <ProjectNameForm
          projectName={projectData.name}
          onUpdateName={(newName: string) =>
            dispatch({ type: "update", key: "name", value: newName })
          }
        />
      ),
    },
    {
      component: (
        <MultiSelect
          options={[
            {
              displayText: "Web application",
              key: "WEB_APP",
              icon: Globe,
            },
            {
              displayText: "Mobile application",
              key: "MOBILE_APP",
              icon: Smartphone,
            },
            {
              displayText: "Desktop application",
              key: "DESKTOP_APP",
              icon: Monitor,
            },
            {
              displayText: "Cloud-based application",
              key: "CLOUD_APP",
              icon: Cloud,
            },
            {
              displayText: "Embedded System / IoT",
              key: "EMBEDDED_IOT",
              icon: Cpu,
            },
            {
              displayText: "API or microservices",
              key: "API_MICROSERVICES",
              icon: Server,
            },
          ]}
          onSelectionChange={(updatedSelection) =>
            dispatch({
              type: "update",
              key: "applicationType",
              value: updatedSelection,
            })
          }
          selectedOptions={projectData.applicationType || []}
        />
      ),
      description: "What type of system are you building?",
    },
    {
      component: (
        <SingleSelect
          options={
            [
              {
                displayText: "Small (< 1.000)",
                key: "SMALL",
                icon: Tally1,
              },
              {
                displayText: "Medium (1.000 - 10.000)",
                key: "MEDIUM",
                icon: Tally2,
              },
              {
                displayText: "Large (10.000 - 100.000)",
                key: "LARGE",
                icon: Tally3,
              },
              {
                displayText: "Enterprise (> 100.000)",
                key: "ENTERPRISE",
                icon: Tally4,
              },
            ] as Array<{
              displayText: string;
              key: NonNullable<ProjectCreationData["userBaseSize"]>;
              // eslint-disable-next-line @typescript-eslint/no-explicit-any
              icon: any;
            }>
          }
          onSelectionChange={(updatedSelection) =>
            dispatch({
              type: "update",
              key: "userBaseSize",
              value: updatedSelection,
            })
          }
          selectedOption={projectData.userBaseSize}
        />
      ),
      description: "What is the expected user base size?",
    },
    {
      component: (
        <SingleSelect
          options={[
            {
              displayText: "Low (e.g., public blogs, forums)",
              key: "LOW",
              icon: Tally1,
            },
            {
              displayText: "Medium (business apps, personal data)",
              key: "MEDIUM",
              icon: Tally2,
            },
            {
              displayText: "High (e.g., financial/healthcare apps)",
              key: "HIGH",
              icon: Tally3,
            },
            {
              displayText: "Very High (e.g., government systems)",
              key: "VERY_HIGH",
              icon: Tally4,
            },
          ]}
          onSelectionChange={(updatedSelection) =>
            dispatch({
              type: "update",
              key: "securityStrengthNeed",
              value: updatedSelection,
            })
          }
          selectedOption={projectData.securityStrengthNeed}
        />
      ),
      description: "How critial is security for this project?",
    },
    {
      component: (
        <MultiSelect
          options={[
            {
              displayText: "Personally identifiable information (PII)",
              key: "PII",
              icon: User,
            },
            { displayText: "Financial data", key: "FINANCIAL", icon: Landmark },
            {
              displayText: "Health data",
              key: "HEALTH",
              icon: HeartPulse,
            },
            {
              displayText: "Intellectual property or proprietary data",
              key: "IP_PROPRIETARY",
              icon: Brain,
            },
            {
              displayText: "Publicly available data",
              key: "PUBLIC",
              icon: BookOpenCheck,
            },
          ]}
          onSelectionChange={(updatedSelection) =>
            dispatch({
              type: "update",
              key: "typeOfData",
              value: updatedSelection,
            })
          }
          selectedOptions={projectData.typeOfData || []}
        />
      ),
      description: "What type of data will your system handle?",
    },
    {
      component: (
        <MultiSelect
          options={[
            {
              displayText: "General Public (e.g., customers, patients)",
              key: "PUBLIC",
              icon: User,
            },
            {
              displayText: "Internal Staff (e.g., employees)",
              key: "INTERNAL",
              icon: Building2,
            },
            {
              displayText: "Privileged Users (e.g. system administrators)",
              key: "PRIVILEGED",
              icon: UserRoundPlus,
            },
            {
              displayText: "Automated Systems (e.g., APIs, IoT devices)",
              key: "AUTOMATED",
              icon: UserCog,
            },
          ]}
          onSelectionChange={(updatedSelection) =>
            dispatch({
              type: "update",
              key: "typeOfUsers",
              value: updatedSelection,
            })
          }
          selectedOptions={projectData.typeOfUsers || []}
        />
      ),
      description: "What type of users will access the system?",
    },
    {
      component: (
        <MultiSelect
          options={[
            {
              displayText: "GDPR",
              key: "GDPR",
            },
            {
              displayText: "HIPAA",
              key: "HIPAA",
            },
            {
              displayText: "PCI-DSS",
              key: "PCI_DSS",
            },
            {
              displayText: "ISO 27001",
              key: "ISO_27001",
            },
          ]}
          onSelectionChange={(updatedSelection) =>
            dispatch({
              type: "update",
              key: "complianceRequirements",
              value: updatedSelection,
            })
          }
          selectedOptions={projectData.complianceRequirements || []}
        />
      ),
      description: "Are there specific compliance requirements? ",
    },
  ]);

  const createProjectMutation = useMutation({
    mutationFn: () => createProject(projectData),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["projects"] });
      resetProjectData();
    },
  });

  const handleProjectCreation = async () => {
    createProjectMutation.mutate();
  };

  return {
    step,
    isFirstStep,
    isLastStep,
    next,
    back,
    resetProjectData,
    handleProjectCreation,
  };
};

export default useProjectCreationForm;
