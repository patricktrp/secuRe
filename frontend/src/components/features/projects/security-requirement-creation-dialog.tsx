import {
  Dialog,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Plus } from "lucide-react";
import { DialogClose, DialogTitle } from "@radix-ui/react-dialog";
import { useState } from "react";
import { createSecurityRequirement } from "@/services/api/projects";
import { useQueryClient } from "@tanstack/react-query";

type SecurityRequirementCreationDialogProps = {
  projectId: string;
};

const SecurityRequirementCreationDialog: React.FC<
  SecurityRequirementCreationDialogProps
> = ({ projectId }) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const queryClient = useQueryClient();

  const handleSecurityRequirementCreation = async () => {
    await createSecurityRequirement(parseInt(projectId), {
      title,
      description,
    });
    setTitle("");
    setDescription("");
    queryClient.invalidateQueries({ queryKey: ["projects", projectId] }); // projectId needs to be a string here because it is in the query as well
  };

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant={"ghost"}>
          <Plus />
        </Button>
      </DialogTrigger>
      <DialogContent className="max-w-[700px]">
        <DialogTitle className="hidden">Create new Security Requirement</DialogTitle>
        <DialogHeader>Create new Security Requirement</DialogHeader>
        <div className="flex flex-col gap-y-2">
          <div>
            <Label>Title</Label>
            <Input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>
          <div>
            <Label>Description</Label>
            <Textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></Textarea>
          </div>
        </div>
        <DialogFooter>
          <DialogClose asChild>
            <Button onMouseDown={handleSecurityRequirementCreation}>
              Create
            </Button>
          </DialogClose>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default SecurityRequirementCreationDialog;
