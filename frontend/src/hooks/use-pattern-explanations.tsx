import { useState, useCallback } from "react";
import axiosInstance from "@/services/api/axiosInstance";

type CacheType = {
  [patternId: string]: string;
};

export const usePatternExplanations = () => {
  const cache: CacheType = {};
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [naturalLanguageExplanation, setNaturalLanguageExplanation] = useState<
    string | null
  >(null);

  const fetchPatternExplanation = useCallback(
    async (patternId: string, securityPattern: unknown, explanations: unknown) => {
      if (cache[patternId]) {
        setNaturalLanguageExplanation(cache[patternId]);
        return cache[patternId];
      }

      setLoading(true);
      setError(null);

      try {
        const response = await axiosInstance.post(
          `/ai/natural-language-explanation`,
          {
            securityPattern,
            explanations,
          }
        );
        const result: string = response.data;

        cache[patternId] = result;

        setNaturalLanguageExplanation(result);

        return result;
      } catch (err) {
        setError("Failed to fetch the natural language explanation.");
        throw err;
      } finally {
        setLoading(false);
      }
    },
    []
  );

  return {
    naturalLanguageExplanation,
    loading,
    error,
    fetchPatternExplanation,
  };
};
