/* eslint-disable @typescript-eslint/no-explicit-any */
import { ExplanationSectionState } from "./recommendations-dialog";
import TextTypingEffectWithTexts from "../text-typing";
import { Skeleton } from "@/components/ui/skeleton";

type RecommendationExplanationProps = {
  state: ExplanationSectionState;
  explanations: any;
  naturalLanguageExplanation: any;
  loading: boolean;
};

const RecommendationExplanation: React.FC<RecommendationExplanationProps> = ({
  state,
  explanations,
  loading,
  naturalLanguageExplanation,
}) => {
  return state === ExplanationSectionState.Log ? (
    <div className="flex flex-col gap-y-3">
      {explanations.map((e: any) => (
        <div className="text-sm">
          <div className="font-semibold flex items-center gap-x-3">
            <div className="flex gap-x-1">
              {e.satisfied ? (
                <div className="h-2 w-2 rounded-full bg-lime-500"></div>
              ) : (
                <div className="h-2 w-2 rounded-full bg-red-500"></div>
              )}
            </div>
            {e.constraintName}
          </div>
          <div className="text-muted-foreground">{e.constraintDescription}</div>
          <div className="flex gap-x-2 mt-1">
            <span className="font-semibold">Rationale: </span>
            <span className="text-muted-foreground">{e.rationale}</span>
          </div>
        </div>
      ))}
    </div>
  ) : !loading && naturalLanguageExplanation ? (
    <TextTypingEffectWithTexts texts={[naturalLanguageExplanation]} />
  ) : (
    <div className="space-y-2">
      <Skeleton className="h-3 w-[250px]" />
      <Skeleton className="h-3 w-[550px]" />
      <Skeleton className="h-3 w-[500px]" />
      <Skeleton className="h-3 w-[550px]" />
      <Skeleton className="h-3 w-[500px]" />
      <Skeleton className="h-3 w-[550px]" />
      <Skeleton className="h-3 w-[500px]" />
      <Skeleton className="h-3 w-[550px]" />
      <Skeleton className="h-3 w-[500px]" />
      <Skeleton className="h-3 w-[550px]" />
      <Skeleton className="h-3 w-[500px]" />
      <Skeleton className="h-3 w-[550px]" />
      <Skeleton className="h-3 w-[500px]" />
    </div>
  );
};

export default RecommendationExplanation;
