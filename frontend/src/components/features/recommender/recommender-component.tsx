import useRecommender from "@/hooks/use-recommender";
import { useState, useEffect } from "react";
import { Answer, Message } from "@/types/types";
import UserMessage from "./chat/user-message";
import AssistantMessage from "./chat/assistant-message";
import { getChatCompletion } from "@/services/api/ai";
import { Input } from "../../ui/input";
import { Button } from "../../ui/button";
import { ScrollArea } from "../../ui/scroll-area";
import RecommendationsDialog from "./recommendations-dialog/recommendations-dialog";

type RecommenderComponentProps = {
  securityControl: number;
  projectId: number;
};

const RecommenderComponent: React.FC<RecommenderComponentProps> = ({
  securityControl,
  projectId,
}) => {
  const { currentQuestion, selectAnswer, securityPatternRecommendations } =
    useRecommender({
      securityControlId: securityControl,
      projectId,
    });

  const [newMessage, setNewMessage] = useState("");
  const [messages, setMessages] = useState<Message[]>([]);
  const [queue, setQueue] = useState<Message[]>([]);
  const [isTyping, setIsTyping] = useState(false);

  const addMessage = (newMessage: Message) => {
    if (newMessage.type === "ASSISTANT") {
      setQueue((prev) => [...prev, newMessage]);
    } else {
      setMessages((prev) => [...prev, newMessage]);
    }
  };

  useEffect(() => {
    if (currentQuestion) {
      addMessage({
        type: "ASSISTANT",
        content: currentQuestion.displayText,
        answers: currentQuestion.answers,
      });
    }
  }, [currentQuestion]);

  const handleChatCompletion = async () => {
    const lastSystemMessage = messages[messages.length - 1];
    const currentMsgs = messages;
    const newMsg = newMessage;
    addMessage({ type: "USER", content: newMessage });
    setNewMessage("");
    const res = await getChatCompletion(projectId, [
      ...currentMsgs,
      { type: "USER", content: newMsg },
    ]);

    addMessage({
      type: "ASSISTANT",
      content: res,
    });
    addMessage(lastSystemMessage);
  };

  useEffect(() => {
    if (!isTyping && queue.length > 0) {
      const nextMessage = queue[0];
      setIsTyping(true);

      let currentText = "";
      const typeInterval = setInterval(() => {
        if (currentText.length < nextMessage.content.length) {
          currentText += nextMessage.content[currentText.length];
          if (currentText.length === 1) {
            setMessages((prevMessages) => [
              ...prevMessages,
              { ...nextMessage, content: currentText }, // Add the updated message
            ]);
          } else {
            setMessages((prevMessages) => [
              ...prevMessages.slice(0, prevMessages.length - 1), // Remove the last message
              { ...nextMessage, content: currentText }, // Add the updated message
            ]);
          }
        } else {
          clearInterval(typeInterval);
          setQueue((prevQueue) => prevQueue.slice(1));
          setIsTyping(false);
        }
      }, 15); // Adjust typing speed
    }
  }, [queue, isTyping]);

  const saveAnswer = (answer: Answer) => {
    addMessage({ type: "USER", content: answer.displayText });
    selectAnswer(answer);
  };

  return (
    <div className="h-full w-full flex flex-col overflow-hidden">
      {securityPatternRecommendations && (
        <RecommendationsDialog
          recommendations={securityPatternRecommendations}
        />
      )}

      <ScrollArea className="h-[600px] px-3">
        <div className="flex flex-col gap-y-3 ">
          {messages.map((message, idx) =>
            message.type === "USER" ? (
              <UserMessage key={idx} content={message.content} />
            ) : (
              <AssistantMessage
                key={idx}
                content={message.content}
                answers={message.answers}
                isLastMessage={idx === messages.length - 1}
                isTyping={isTyping}
                onSaveAnswer={saveAnswer}
              />
            )
          )}
        </div>
      </ScrollArea>

      <div className="flex items-center justify-center gap-x-2 px-10 border-t h-20 mt-2">
        <Input
          className="flex-1"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Type your message..."
        />
        <Button onClick={handleChatCompletion}>Send</Button>
      </div>
    </div>
  );
};

export default RecommenderComponent;
