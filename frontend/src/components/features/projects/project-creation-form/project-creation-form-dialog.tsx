import { ReactNode } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import useProjectCreationForm from "@/hooks/use-project-creation-form";

type ProjectCreationFormDialogProps = {
  trigger: ReactNode;
};

const ProjectCreationFormDialog: React.FC<ProjectCreationFormDialogProps> = ({
  trigger,
}) => {
  const [open, setOpen] = useState(false);
  const {
    step,
    isFirstStep,
    isLastStep,
    next,
    back,
    resetProjectData,
    handleProjectCreation,
  } = useProjectCreationForm();

  const handleSave = () => {
    handleProjectCreation();
    setOpen(false);
  };

  const handleOpenChange = () => {
    resetProjectData();
    setOpen((prev) => !prev);
  };

  return (
    <Dialog open={open} onOpenChange={handleOpenChange}>
      <DialogTrigger asChild>{trigger}</DialogTrigger>
      <DialogContent className="min-w-[750px] max-w-lg">
        <DialogHeader>
          <DialogTitle>Create a new Project</DialogTitle>
          <DialogDescription>{step.description}</DialogDescription>
        </DialogHeader>
        {step.component}
        <DialogFooter>
          <div className="w-full flex justify-between">
            {!isFirstStep ? <Button variant={"outline"} onClick={back}>Back</Button> : <div></div>}
            {!isLastStep ? (
              <Button onClick={next}>Next</Button>
            ) : (
              <Button onClick={handleSave}>Save</Button>
            )}
          </div>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default ProjectCreationFormDialog;
