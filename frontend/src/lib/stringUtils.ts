export const firstLetterToUppercase = (input: string | undefined) => {
  if (input === undefined) return "";
  return input[0].toLocaleUpperCase() + input.slice(1, input.length);
};

export const toLowerCaseWithFirstLetterUppercase = (input: string): string => {
  if (!input) return input;
  const lowerCased = input.toLowerCase();
  return lowerCased.charAt(0).toUpperCase() + lowerCased.slice(1);
};
