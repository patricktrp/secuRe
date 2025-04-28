import { useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { Separator } from "@/components/ui/separator";
import { SidebarTrigger } from "@/components/ui/sidebar";
import RecommenderSetupDialog from "@/components/features/recommender/recommender-setup-dialog";
import RecommenderComponent from "@/components/features/recommender/recommender-component";
import { SecurityControl, SecurityRequirement, Project } from "@/types/types";

enum RecommenderState {
  Idle,
  Initialization,
  InProgress,
  Done,
}

type RecommendationSessionData = {
  project: Project | null;
  securityControl: SecurityControl | null;
  securityRequirement: SecurityRequirement | null;
};

const Recommender = () => {
  const [recommenderState, setRecommenderState] = useState<RecommenderState>(
    RecommenderState.Idle
  );

  const [recommendationSessionData, setRecommendationSessionData] =
    useState<RecommendationSessionData>({
      project: null,
      securityControl: null,
      securityRequirement: null,
    });

  const handleInitializationSessionFinish = (
    project: Project,
    securityControl: SecurityControl,
    securityRequirement: SecurityRequirement
  ) => {
    setRecommendationSessionData({
      project,
      securityControl,
      securityRequirement,
    });
    setRecommenderState(RecommenderState.InProgress);
  };

  return (
    <div className="h-full flex flex-col justify-between">
      <RecommenderSetupDialog
        open={recommenderState === RecommenderState.Initialization}
        onClose={() => setRecommenderState(RecommenderState.Idle)}
        onInitializationSessionFinish={handleInitializationSessionFinish}
      />

      <header className="flex h-16 shrink-0 items-center gap-2">
        <div className="flex items-center gap-2 px-4">
          <SidebarTrigger className="-ml-1" />
          <Separator orientation="vertical" className="mr-2 h-4" />
          <Breadcrumb>
            <BreadcrumbList>
              <BreadcrumbItem className="hidden md:block">
                Recommender
              </BreadcrumbItem>
              {recommendationSessionData && (
                <>
                  <BreadcrumbSeparator />
                  <BreadcrumbItem className="hidden md:block">
                    {recommendationSessionData?.project?.name}
                  </BreadcrumbItem>
                  <BreadcrumbSeparator />
                  <BreadcrumbItem className="hidden md:block">
                    {recommendationSessionData?.securityRequirement?.title}
                  </BreadcrumbItem>
                </>
              )}
            </BreadcrumbList>
          </Breadcrumb>
        </div>
      </header>

      <main className="w-full h-full px-4">
        {recommenderState === RecommenderState.Idle && (
          <div className="h-full flex items-center justify-center">
            <Button
              onClick={() =>
                setRecommenderState(RecommenderState.Initialization)
              }
            >
              Start new Session
            </Button>
          </div>
        )}

        {recommenderState === RecommenderState.InProgress &&
          recommendationSessionData?.securityControl?.id &&
          recommendationSessionData?.project?.id && (
            <RecommenderComponent
              securityControl={recommendationSessionData?.securityControl?.id}
              projectId={recommendationSessionData?.project?.id}
            />
          )}
      </main>
    </div>
  );
};

export default Recommender;
