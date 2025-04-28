import { SecurityPattern } from "@/types/types";
import Attribute from "../attribute";

type RecommendationDescriptionProps = {
  securityPattern: SecurityPattern;
};

const RecommendationDescription: React.FC<RecommendationDescriptionProps> = ({
  securityPattern,
}) => {
  return (
    <div className="text-sm flex flex-col gap-y-4">
      <Attribute
        heading="Intent"
        content={securityPattern.description.intent}
      />
      <Attribute
        heading="Problem"
        content={securityPattern.description.problem}
      />
      <Attribute
        heading="Solution"
        content={securityPattern.description.solution}
      />
    </div>
  );
};

export default RecommendationDescription;
