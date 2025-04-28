import { Bot } from "lucide-react";
import Markdown from "react-markdown";
import { Button } from "@/components/ui/button";
import { Answer } from "@/types/types";
import { useState } from "react";

type AssistantMessageProps = {
  content: string;
  answers?: Answer[];
  onSaveAnswer?: (answer: Answer) => void;
  isLastMessage?: boolean;
  isTyping?: boolean;
};

const AssistantMessage: React.FC<AssistantMessageProps> = ({
  content,
  answers,
  onSaveAnswer,
  isLastMessage,
  isTyping,
}) => {
  const [showAnswers, setShowAnswers] = useState(true);

  const submitAnswer = (answer: Answer) => {
    if (onSaveAnswer) {
      onSaveAnswer(answer);
      setShowAnswers(false);
    }
  };

  return (
    <div className="flex justify-start max-w-[50%] items-start gap-x-3">
      <div className="rounded-full bg-sidebar min-w-10 min-h-10 flex items-center justify-center">
        <Bot />
      </div>
      <div className="flex flex-col">
        <Markdown className="py-2 px-4 bg-sidebar rounded-lg">
          {content}
        </Markdown>
        {answers && showAnswers && !isTyping && isLastMessage && (
          <div className="flex flex-col gap-y-2 mt-2 animate-fade-in">
            {answers.map((answer) => (
              <Button
                key={answer.answerKey}
                onClick={() => submitAnswer(answer)}
                className="transition-transform transform scale-100 opacity-100 animate-scale-up max-w-72"
              >
                {answer?.displayText}
              </Button>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default AssistantMessage;
