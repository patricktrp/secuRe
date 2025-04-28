import { SecurityPattern } from "@/types/types";
import Attribute from "../attribute";
import { mapProperty } from "@/lib/utils";

type RecommendationPropertiesProps = {
  securityPattern: SecurityPattern;
};

const RecommendationProperties: React.FC<RecommendationPropertiesProps> = ({
  securityPattern,
}) => {
  return (
    <div className="text-sm grid grid-cols-2 gap-x-6 gap-y-4">
      {Object.entries(securityPattern.properties).map(([key, value]) => (
        <Attribute
          heading={`${mapProperty(key)} - ${value.score}`}
          content={value.rationale}
        />
      ))}
    </div>
  );
};

export default RecommendationProperties;
