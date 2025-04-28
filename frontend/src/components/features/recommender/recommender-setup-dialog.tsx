import { useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Project,
  SECURITY_CONTROLS,
  SecurityControl,
  SecurityRequirement,
} from "@/types/types";
import { Button } from "@/components/ui/button";
import BarLoader from "react-spinners/BarLoader";
import { useProjects } from "@/hooks/use-projects";

type RecommenderSetupDialogProps = {
  open: boolean;
  onClose: () => void;
  onInitializationSessionFinish: (
    project: Project,
    securityControl: SecurityControl,
    securityRequirement: SecurityRequirement
  ) => void;
};

const RecommenderSetupDialog: React.FC<RecommenderSetupDialogProps> = ({
  open,
  onClose,
  onInitializationSessionFinish,
}) => {
  const {
    isLoading,
    projects,
    selectedProject,
    selectedSecurityControl,
    selectedSecurityRequirement,
    selectProject,
    selectSecurityControl,
    selectSecurityRequirement,
  } = useProjects(open);

  const [step, setStep] = useState<number>(1);

  const dialogTitles: Record<number, string> = {
    1: "Select a Project",
    2: "Select a Security Requirement",
    3: "Select a Security Control",
  };

  return (
    <Dialog
      open={open}
      onOpenChange={(open) => {
        if (!open) {
          onClose();
          setStep(1);
        }
      }}
    >
      <DialogContent className="max-w-lg">
        <DialogHeader>
          <DialogTitle>{dialogTitles[step]}</DialogTitle>
        </DialogHeader>

        {isLoading ? (
          <div className="w-full min-h-56 flex items-center justify-center">
            <BarLoader />
          </div>
        ) : (
          <div>
            <div className="flex flex-col gap-y-3">
              {step === 1 &&
                (projects.length > 0 ? (
                  projects.map((project) => (
                    <Button
                      key={project.id}
                      onClick={() => {
                        selectProject(project.id);
                        setStep(2);
                      }}
                      variant={"secondary"}
                    >
                      {project.name}
                    </Button>
                  ))
                ) : (
                  <div>No Projects Found.</div>
                ))}
            </div>

            {step === 2 && selectedProject !== null && (
              <div className="flex flex-col gap-y-3">
                {selectedProject.securityRequirements.map((sr) => (
                  <Button
                    onClick={() => {
                      selectSecurityRequirement(sr.id);
                      setStep(3);
                    }}
                    variant={"secondary"}
                    key={sr.id}
                  >
                    {sr.title}
                  </Button>
                ))}
                {selectedProject.securityRequirements.length === 0 && (
                  <div>No security requirements found for this project.</div>
                )}
              </div>
            )}

            {step === 3 &&
              SECURITY_CONTROLS.map((sc) => (
                <Button
                  onClick={() => {
                    selectSecurityControl(sc.id);
                    if (
                      selectedProject &&
                      selectedSecurityControl &&
                      selectedSecurityRequirement
                    ) {
                      onInitializationSessionFinish(
                        selectedProject,
                        selectedSecurityControl,
                        selectedSecurityRequirement
                      );
                    }
                  }}
                  variant={"secondary"}
                  key={sc.id}
                >
                  {sc.type}
                </Button>
              ))}
          </div>
        )}
      </DialogContent>
    </Dialog>
  );
};

export default RecommenderSetupDialog;
