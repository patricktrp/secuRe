/* eslint-disable @typescript-eslint/no-explicit-any */
import {
  getSecurityPatternRecommendations,
  getInputDialogBySecurityControlId,
} from "@/services/api/recommendations";
import { Answer } from "@/types/types";
import { useEffect, useState } from "react";

export interface UseRecommenderProps {
  securityControlId: number;
  projectId: number;
}

const useRecommender = ({
  securityControlId,
  projectId,
}: UseRecommenderProps) => {
  const [questionnaire, setQuestionnaire] = useState<any>(null);
  const [answers, setAnswers] = useState({});
  const [currentQuestionKey, setCurrentQuestionKey] = useState<string>("");
  const [securityPatternRecommendations, setSecurityPatternRecommendations] =
    useState(null);

  useEffect(() => {
    const getQuestionnaire = async () => {
      if (!securityControlId) {
        return;
      }
      const res = await getInputDialogBySecurityControlId(securityControlId);
      setQuestionnaire(res.preferenceElicitationDialog);

      if (res && res.preferenceElicitationDialog.entryPoint) {
        setCurrentQuestionKey(res.preferenceElicitationDialog.entryPoint);
      }
    };

    getQuestionnaire();
  }, [securityControlId]);

  useEffect(() => {
    const fetchScpRecommendations = async () => {
      const data = await getSecurityPatternRecommendations(
        projectId,
        securityControlId,
        answers
      );
      setSecurityPatternRecommendations(data);
    };

    if (currentQuestionKey === "end") {
      fetchScpRecommendations();
    }
  }, [currentQuestionKey]);

  const currentQuestion =
    questionnaire && currentQuestionKey
      ? questionnaire[currentQuestionKey]
      : null;

  const isFinished = questionnaire && currentQuestionKey === "end";

  const selectAnswer = (newAnswer: Answer) => {
    setAnswers((prev) => ({
      ...prev,
      [currentQuestionKey]: newAnswer.answerKey,
    }));
    setCurrentQuestionKey(newAnswer.nextQuestion);
  };

  return {
    currentQuestion,
    isFinished,
    answers,
    selectAnswer,
    securityPatternRecommendations,
  };
};

export default useRecommender;
