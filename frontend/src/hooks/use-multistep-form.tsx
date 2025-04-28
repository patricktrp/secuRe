import { useState, ReactElement } from "react";

type Step = {
  component: ReactElement;
  description: string;
};

/**
 * A hook to help building multi-step forms 
 * @param steps Each step needs a component that can be rendered and a description 
 * @returns Utilities to navigate through a multi-step form, e.g. next and back
 */
export const useMultiStepForm = (steps: Step[]) => {
  const [currentStepIdx, setCurrentStepIdx] = useState(0);

  const next = () => {
    setCurrentStepIdx((i) => {
      if (i >= steps.length - 1) return i;
      return i + 1;
    });
  };

  const back = () => {
    setCurrentStepIdx((i) => {
      if (i <= 0) return i;
      return i - 1;
    });
  };

  const goTo = (index: number) => {
    setCurrentStepIdx(index);
  };

  return {
    currentStepIdx,
    step: steps[currentStepIdx],
    goTo,
    next,
    back,
    steps,
    isFirstStep: currentStepIdx === 0,
    isLastStep: currentStepIdx === steps.length - 1,
  };
};
