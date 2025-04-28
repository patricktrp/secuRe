"use client";

import { ChevronDown, Files } from "lucide-react";
import { CircularProgressbar, buildStyles } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { useState, useEffect } from "react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

import { jsPDF } from "jspdf";
import RecommendationProperties from "./recommendation-properties";
import { Dialog, DialogContent, DialogTitle } from "@/components/ui/dialog";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarProvider,
} from "@/components/ui/sidebar";
import { SecurityPatternRecommendation } from "@/types/types";
import { usePatternExplanations } from "@/hooks/use-pattern-explanations";
import RecommendationDescription from "./recommendation-description";
import RecommendationExplanation from "./recommendations-explanation";

enum SectionState {
  Description = "Description",
  Properties = "Properties",
  Explanations = "Explanations",
}

export enum ExplanationSectionState {
  Log = "Log",
  NaturalLanguage = "Natural Language",
}

type RecommendationsDialogProps = {
  recommendations: SecurityPatternRecommendation[];
};

const RecommendationsDialog: React.FC<RecommendationsDialogProps> = ({
  recommendations,
}) => {
  const [open, setOpen] = useState(true);
  const [selectedRecommendation, setSelectedRecommendation] = useState(
    recommendations[0]
  );
  const {
    naturalLanguageExplanation,
    loading,
    error,
    fetchPatternExplanation,
  } = usePatternExplanations();

  const [activeSection, setActiveSection] = useState<SectionState>(
    SectionState.Description
  );
  const [activeExplanationSection, setActiveExplanationSection] =
    useState<ExplanationSectionState>(ExplanationSectionState.Log);

  useEffect(() => {
    if (activeExplanationSection === ExplanationSectionState.NaturalLanguage) {
      fetchPatternExplanation(
        selectedRecommendation.securityPattern.id,
        selectedRecommendation.securityPattern,
        selectedRecommendation.explanations
      ).catch(console.error);
    }
  }, [
    selectedRecommendation.securityPattern.id,
    fetchPatternExplanation,
    activeExplanationSection,
  ]);

  const handleMenuClick = (recommendation: SecurityPatternRecommendation) => {
    setSelectedRecommendation(recommendation);
  };

  const score = Math.round(selectedRecommendation.score * 100);

  const generatePDF = () => {
    const doc = new jsPDF();
    doc.save("recommendations.pdf");
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogContent className="overflow-hidden p-0 md:max-h-[500px] md:max-w-[1000px] lg:max-w-[1000px] [&>button]:hidden">
        <DialogTitle className="sr-only">Settings</DialogTitle>

        <SidebarProvider className="items-start">
          <Sidebar collapsible="none" className="hidden md:flex">
            <SidebarHeader>
              <SidebarMenu>
                <SidebarMenuItem>
                  <SidebarMenuButton
                    size="lg"
                    asChild
                    className="cursor-pointer"
                    onClick={() => generatePDF()}
                  >
                    <div className="flex justify-between">
                      <div className="flex items-center gap-x-2">
                        <div className="flex aspect-square size-8 items-center justify-center rounded-lg bg-sidebar-primary text-sidebar-primary-foreground">
                          <Files className="size-4" />
                        </div>
                        <div className="grid flex-1 text-left text-sm leading-tight">
                          <span className="truncate font-medium">
                            Recommendations
                          </span>
                        </div>
                      </div>
                    </div>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              </SidebarMenu>
            </SidebarHeader>
            <SidebarContent className="flex flex-col justify-between h-full">
              <SidebarGroup>
                <SidebarGroupContent>
                  <SidebarMenu>
                    {recommendations.map((recommendation, idx) => (
                      <SidebarMenuItem
                        key={recommendation.securityPattern.name}
                      >
                        <SidebarMenuButton
                          asChild
                          isActive={
                            selectedRecommendation.securityPattern.name ===
                            recommendation.securityPattern.name
                          }
                        >
                          <div
                            className="cursor-pointer"
                            onClick={() => handleMenuClick(recommendation)}
                          >
                            <span>
                              {idx + 1}. {recommendation.securityPattern.name}
                            </span>
                          </div>
                        </SidebarMenuButton>
                      </SidebarMenuItem>
                    ))}
                  </SidebarMenu>
                </SidebarGroupContent>
              </SidebarGroup>
            </SidebarContent>
          </Sidebar>
          <main className="flex h-[480px] flex-1 flex-col overflow-hidden">
            <header className="px-4 items-center flex justify-between h-16 shrink-0 gap-2 transition-[width,height] ease-linear group-has-[[data-collapsible=icon]]/sidebar-wrapper:h-12">
              <div className="flex items-center gap-2">
                <Breadcrumb>
                  <BreadcrumbList>
                    <BreadcrumbItem>
                      <BreadcrumbPage>
                        {selectedRecommendation.securityPattern.name}
                      </BreadcrumbPage>
                    </BreadcrumbItem>
                    <BreadcrumbSeparator className="hidden md:block" />
                    <BreadcrumbItem>
                      <DropdownMenu>
                        <DropdownMenuTrigger className="flex items-center gap-1">
                          {activeSection}
                          <ChevronDown />
                        </DropdownMenuTrigger>
                        <DropdownMenuContent align="start">
                          <DropdownMenuItem
                            onClick={() =>
                              setActiveSection(SectionState.Description)
                            }
                          >
                            Description
                          </DropdownMenuItem>
                          <DropdownMenuItem
                            onClick={() =>
                              setActiveSection(SectionState.Properties)
                            }
                          >
                            Properties
                          </DropdownMenuItem>
                          <DropdownMenuItem
                            onClick={() =>
                              setActiveSection(SectionState.Explanations)
                            }
                          >
                            Explanation
                          </DropdownMenuItem>
                        </DropdownMenuContent>
                      </DropdownMenu>
                    </BreadcrumbItem>
                    {activeSection === SectionState.Explanations && (
                      <BreadcrumbSeparator />
                    )}
                    {activeSection === SectionState.Explanations && (
                      <DropdownMenu>
                        <DropdownMenuTrigger className="flex items-center gap-1">
                          {activeExplanationSection}
                          <ChevronDown />
                        </DropdownMenuTrigger>
                        <DropdownMenuContent align="start">
                          <DropdownMenuItem
                            onClick={() =>
                              setActiveExplanationSection(
                                ExplanationSectionState.Log
                              )
                            }
                          >
                            Log
                          </DropdownMenuItem>
                          <DropdownMenuItem
                            onClick={() =>
                              setActiveExplanationSection(
                                ExplanationSectionState.NaturalLanguage
                              )
                            }
                          >
                            Natural Text
                          </DropdownMenuItem>
                        </DropdownMenuContent>
                      </DropdownMenu>
                    )}
                  </BreadcrumbList>
                </Breadcrumb>
              </div>

              <div className="w-12 h-12">
                <CircularProgressbar
                  value={Math.round(score)}
                  text={`${score}`}
                  strokeWidth={10}
                  styles={buildStyles({
                    textColor: "#000",
                    pathColor: "#171717",
                    trailColor: "#d6d6d6",
                    textSize: "20px",
                    strokeLinecap: "round",
                  })}
                />
              </div>
            </header>

            <div className="flex flex-1 flex-col gap-4 overflow-y-auto p-4 pt-0">
              <div>
                {activeSection === SectionState.Description && (
                  <RecommendationDescription
                    securityPattern={selectedRecommendation.securityPattern}
                  />
                )}

                {activeSection === SectionState.Properties && (
                  <RecommendationProperties
                    securityPattern={selectedRecommendation.securityPattern}
                  />
                )}

                {activeSection === SectionState.Explanations && (
                  <RecommendationExplanation
                    state={activeExplanationSection}
                    explanations={selectedRecommendation.explanations}
                    naturalLanguageExplanation={naturalLanguageExplanation}
                    loading={loading}
                  />
                )}
              </div>
            </div>
          </main>
        </SidebarProvider>
      </DialogContent>
    </Dialog>
  );
};

export default RecommendationsDialog;
