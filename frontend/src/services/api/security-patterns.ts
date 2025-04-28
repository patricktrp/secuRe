import { SecurityPattern } from "@/types/types";
import axiosInstance from "./axiosInstance";

const getSecurityPatterns = async (): Promise<SecurityPattern[]> => {
  try {
    const res = await axiosInstance.get("/security-patterns");
    return res.data;
  } catch (error) {
    console.error("Error fetching security patterns:", error);
    throw error;
  }
};

const getSecurityPatternbyId = async (
  securityPatternId: string
): Promise<SecurityPattern> => {
  try {
    const res = await axiosInstance.get(
      `/security-patterns/${securityPatternId}`
    );
    return res.data;
  } catch (error) {
    console.error(
      `Error fetching security pattern by ID (${securityPatternId}):`,
      error
    );
    throw error;
  }
};

export { getSecurityPatterns, getSecurityPatternbyId };
