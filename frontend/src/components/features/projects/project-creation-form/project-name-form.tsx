import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

type ProjectNameFormProps = {
  projectName: string;
  onUpdateName: (newName: string) => void;
};

const ProjectNameForm: React.FC<ProjectNameFormProps> = ({
  projectName,
  onUpdateName,
}) => {
  return (
    <div className="w-full">
      <Label>Project Name</Label>
      <Input
        value={projectName}
        onChange={(e) => onUpdateName(e.target.value)}
      />
    </div>
  );
};

export default ProjectNameForm;
