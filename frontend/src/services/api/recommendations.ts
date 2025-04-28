import axiosInstance from "./axiosInstance";

const getSecurityPatternRecommendations = async (
  projectId: number,
  securityControlId: number,
  userPreferences: object
) => {
  try {
    const res = await axiosInstance.post("/recommendations/security-patterns", {
      securityControlId,
      projectId,
      userPreferences,
    });
    return res.data;
  } catch (error) {
    console.error(
      `Error fetching security pattern recommendations for project ID (${projectId}) and security control ID (${securityControlId}):`,
      error
    );
    throw error;
  }
};

const getInputDialogBySecurityControlId = async (securityControlId: number) => {
  try {
    const searchParams = new URLSearchParams({
      securityControlId: `${securityControlId}`,
    });

    const res = await axiosInstance.get(
      `/recommendations/preference-elicitation-dialogs?${searchParams.toString()}`
    );

    return res.data;
  } catch (error) {
    console.error(
      `Error fetching input dialog for security control ID (${securityControlId}):`,
      error
    );
    throw error;
  }
};

export { getSecurityPatternRecommendations, getInputDialogBySecurityControlId };
