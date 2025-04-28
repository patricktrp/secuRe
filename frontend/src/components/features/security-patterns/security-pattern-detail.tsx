import { SecurityPattern } from "@/types/types";
import { Badge } from "@/components/ui/badge";
import { Bot, Plus, User, Minus } from "lucide-react";

type SecurityPatternProps = {
  securityPattern: SecurityPattern;
};

const SecurityPatternRender: React.FC<SecurityPatternProps> = ({
  securityPattern,
}) => {
  return (
    <div className="w-full">
      <div className="flex gap-x-3 items-center">
        <h1 className="scroll-m-20 text-xl font-semibold tracking-tight">
          {securityPattern?.name}
        </h1>
      </div>
      <div className="flex gap-x-2 mt-2 items-center">
        <Badge variant={"secondary"} className="h-5">
          {securityPattern?.securityControl.type}
        </Badge>
      </div>
      <div className="flex flex-col gap-y-6 mt-4">
        <div>
          <h3 className="font-semibold text-sm">Intent</h3>
          <p className="text-sm text-muted-foreground leading-6 [&:not(:first-child)]:mt-1">
            {securityPattern?.description.intent}
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-2">
          <div>
            <h3 className="font-semibold text-sm">Problem</h3>
            <p className="text-sm text-muted-foreground leading-6 [&:not(:first-child)]:mt-1">
              {securityPattern?.description.problem}
            </p>
          </div>

          <div>
            <h3 className="font-semibold text-sm">Solution</h3>
            <p className="text-sm text-muted-foreground leading-6 [&:not(:first-child)]:mt-1">
              {securityPattern?.description.solution}
            </p>
          </div>
        </div>

        <div className="mt-4">
          <h3 className="font-semibold text-sm">Consequences</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-2">
            <div>
              <div className="flex items-center gap-x-2">
                <div className="size-6 rounded-full bg-muted flex justify-center items-center">
                  <Plus className="size-4" />
                </div>
                <h3>Positive</h3>
              </div>
              <ul className="flex flex-col gap-y-2 mt-2">
                {securityPattern?.description?.consequences?.positive.map(
                  (positiveConsequence) => (
                    <li
                      className="flex flex-col"
                      key={positiveConsequence.factor}
                    >
                      <div className="text-sm">
                        {positiveConsequence.factor}
                      </div>
                      <p className="text-sm text-muted-foreground leading-6">
                        {positiveConsequence.reason}
                      </p>
                    </li>
                  )
                )}
              </ul>
            </div>
            <div>
              <div className="flex items-center gap-x-2">
                <div className="size-6 rounded-full bg-muted flex justify-center items-center">
                  <Minus className="size-4" />
                </div>
                <h3>Negative</h3>
              </div>
              <ul className="flex flex-col gap-y-2 mt-2">
                {securityPattern?.description?.consequences?.negative.map(
                  (negativeConsequence) => (
                    <li
                      className="flex flex-col"
                      key={negativeConsequence.factor}
                    >
                      <div className="text-sm">
                        {negativeConsequence.factor}
                      </div>
                      <p className="text-sm text-muted-foreground leading-6">
                        {negativeConsequence.reason}
                      </p>
                    </li>
                  )
                )}
              </ul>
            </div>
          </div>
        </div>

        <div>
          <h3 className="font-semibold text-sm">Participants</h3>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-2">
            <div>
              <div className="flex items-center gap-x-2">
                <div className="size-6 rounded-full bg-muted flex justify-center items-center">
                  <User className="size-4" />
                </div>
                <h3>Human</h3>
              </div>
              <ul className="flex flex-col gap-y-2 mt-2">
                {securityPattern?.description?.participants?.human.map(
                  (humanParticipant) => (
                    <li className="flex flex-col" key={humanParticipant.name}>
                      <div className="text-sm">{humanParticipant.name}</div>
                      <p className="text-sm text-muted-foreground leading-6">
                        {humanParticipant.description}
                      </p>
                    </li>
                  )
                )}
              </ul>
            </div>
            <div>
              <div className="flex items-center gap-x-2">
                <div className="size-6 rounded-full bg-muted flex justify-center items-center">
                  <Bot className="size-4" />
                </div>
                <h3>Non-Human</h3>
              </div>
              <ul className="flex flex-col gap-y-2 mt-2">
                {securityPattern?.description?.participants?.nonHuman?.map(
                  (nonHumanParticipant) => (
                    <li
                      className="flex flex-col"
                      key={nonHumanParticipant.name}
                    >
                      <div className="text-sm">{nonHumanParticipant.name}</div>
                      <p className="text-sm text-muted-foreground leading-6">
                        {nonHumanParticipant.description}
                      </p>
                    </li>
                  )
                )}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SecurityPatternRender;
