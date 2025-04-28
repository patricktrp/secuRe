import { Button } from "@/components/ui/button";
import { LucideIcon } from "lucide-react";

type Option<T extends string> = {
  icon?: LucideIcon;
  displayText: string;
  key: T;
};

type SingleSelectProps<T extends string> = {
  options: Option<T>[];
  selectedOption: T | null;
  onSelectionChange: (updatedSelection: T | null) => void;
};

const SingleSelect = <T extends string>({
  options,
  selectedOption,
  onSelectionChange,
}: SingleSelectProps<T>) => {
  const handleOnClick = (option: Option<T>) => {
    const updatedSelection = selectedOption === option.key ? null : option.key;
    onSelectionChange(updatedSelection);
  };

  return (
    <div>
      <div className="grid grid-cols-2 gap-x-2 gap-y-2">
        {options.map((option) => (
          <Button
            variant={`${selectedOption === option.key ? "default" : "outline"}`}
            onClick={() => handleOnClick(option)}
            key={option.key}
            className={`flex ${
              option.icon ? "justify-start" : "justify-center"
            }`}
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

export default SingleSelect;
