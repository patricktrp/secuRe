import { projectProperties } from "@/constants/project-properties";
import { Project } from "@/types/types";

type ProjectPropertyProps = {
  propertyKey: keyof typeof projectProperties;
  propertyValue: Project["properties"][keyof Project["properties"]];
};

const ProjectProperty: React.FC<ProjectPropertyProps> = ({
  propertyKey,
  propertyValue,
}) => {
  const placeholder = projectProperties[propertyKey].options;

  const propertyValueList = Array.isArray(propertyValue)
    ? propertyValue
    : [propertyValue];

  return (
    <div>
      <h3 className="font-semibold text-sm">
        {projectProperties[propertyKey].metadata.displayName}
      </h3>
      <div className="flex flex-col mt-1">
        {propertyValueList.map((propValue, idx) => {
          if (!propValue) return null;
          //@ts-expect-error: We know that the propertyKey is a key of projectProperties
          const op = projectProperties[propertyKey].options[propValue];
          return (
            <div key={idx} className="flex items-center gap-x-2">
              {op.icon && <op.icon className="size-4" />}
              {/*@ts-expect-error: We know that the propertyKey is a key of projectProperties */}
              {placeholder[propValue].displayText}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ProjectProperty;
