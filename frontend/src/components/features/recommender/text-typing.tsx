import { useTypingEffect } from "@/hooks/use-typing-effect";
import { useState, useEffect } from "react";
import Markdown from "react-markdown";

type TextTypingEffectProps = {
  isTypeByLetter?: boolean;
  duration?: number;
  texts: string[];
};

export const TextTypingEffectWithTexts: React.FC<TextTypingEffectProps> = ({
  isTypeByLetter = true,
  duration = 10,
  texts,
}) => {
  const [textIndex, setTextIndex] = useState(0);
  const textToShow = useTypingEffect(
    texts[textIndex],
    duration,
    isTypeByLetter
  );

  useEffect(() => {
    const intervalId = setInterval(() => {
      setTextIndex((prevIndex) =>
        prevIndex >= texts.length - 1 ? 0 : prevIndex + 1
      );
    }, 5000);

    return () => {
      clearInterval(intervalId);
    };
  }, []);

  return (
    <Markdown className="text-black dark:text-white text-sm" key={textIndex}>
      {textToShow}
    </Markdown>
  );
};

export default TextTypingEffectWithTexts;
