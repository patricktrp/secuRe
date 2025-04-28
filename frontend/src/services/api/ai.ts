import axiosInstance from "./axiosInstance";
import { Message } from "@/types/types";

// Enrich the message content by appending the possible answer choices if available
const enrichMessages = (messages: Message[]): Message[] => {
  return messages.map((message) => {
    if (message.answers && message.answers.length > 0) {
      const answerList = message.answers
        .map((answer) => answer.displayText)
        .join(", ");
      return {
        type: message.type,
        content: `${message.content} (the user has the following answer possibilities: ${answerList})`,
      };
    }
    return message;
  });
};

// Get chat completion from the backend with enriched messages
const getChatCompletion = async (
  projectId: number,
  messages: Message[]
): Promise<any> => {
  try {
    // Enrich messages before sending
    const enrichedMessages = enrichMessages(messages);

    // Make API call to fetch the chat completion
    const res = await axiosInstance.post("/ai/chat-completion", {
      messages: enrichedMessages,
      projectId,
    });

    return res.data;
  } catch (error) {
    // Handle errors gracefully
    console.error("Error fetching chat completion:", error);
    throw error;
  }
};

export { getChatCompletion };
