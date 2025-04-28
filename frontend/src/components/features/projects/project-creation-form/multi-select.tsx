import { Button } from "@/components/ui/button";
import { LucideIcon } from "lucide-react";

type Option<T extends string> = {
  icon?: LucideIcon;
  displayText: string;
  key: T;
};

type MultiSelectProps<T extends string> = {
  options: Option<T>[];
  selectedOptions: T[];
  onSelectionChange: (updatedSelection: T[]) => void;
};

const MultiSelect = <T extends string>({
  options,
  selectedOptions,
  onSelectionChange,
}: MultiSelectProps<T>) => {
  const handleOnClick = (option: Option<T>) => {
    let updatedSelection;
    if (selectedOptions.includes(option.key)) {
      updatedSelection = selectedOptions.filter((item) => item !== option.key);
    } else {
      updatedSelection = [...selectedOptions, option.key];
    }
    onSelectionChange(updatedSelection);
  };

  return (
    <div>
      <div className="grid grid-cols-2 gap-x-2 gap-y-2">
        {options.map((option) => (
          <Button
            variant={`${
              selectedOptions.includes(option.key) ? "default" : "outline"
            }`}
            onClick={() => handleOnClick(option)}
            key={option.key}
            className={`flex ${option.icon ? "justify-start" : "justify-center"}`}
          >
            {option.icon && (
              <div className="w-5">
                <option.icon />
              </div>
            )}
            {option.displayText}
          </Button>
        ))}
      </div>
    </div>
  );
};

export default MultiSelect;
