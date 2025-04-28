import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { Separator } from "@/components/ui/separator";
import { SidebarTrigger } from "@/components/ui/sidebar";
import { NavLink } from "react-router-dom";
import { useParams } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import SecurityPatternRender from "@/components/features/security-patterns/security-pattern-detail";

import { getSecurityPatternbyId } from "@/services/api/security-patterns";

const SecurityPatternDetail = () => {
  const { securityPatternId } = useParams();

  const { data } = useQuery({
    queryKey: ["security-patterns", securityPatternId],
    queryFn: () => {
      if (securityPatternId) {
        return getSecurityPatternbyId(securityPatternId);
      }
      throw new Error("securitySolutionPattern ID is undefined");
    },
  });

  return (
    <div className="h-full">
      <header className="flex h-16 shrink-0 items-center gap-2">
        <div className="flex items-center gap-2 px-4">
          <SidebarTrigger className="-ml-1" />
          <Separator orientation="vertical" className="mr-2 h-4" />
          <Breadcrumb>
            <BreadcrumbList>
              <BreadcrumbItem className="hidden md:block">
                <NavLink to="/security-patterns">
                  <BreadcrumbLink>Security Patterns</BreadcrumbLink>
                </NavLink>
              </BreadcrumbItem>
              <BreadcrumbSeparator />
              <BreadcrumbItem className="hidden md:block">
                <BreadcrumbLink>{data?.name}</BreadcrumbLink>
              </BreadcrumbItem>
            </BreadcrumbList>
          </Breadcrumb>
        </div>
      </header>

      <main className="py-4 px-12 flex gap-x-10 items-start">
        {data && <SecurityPatternRender securityPattern={data} />}
      </main>
    </div>
  );
};

export default SecurityPatternDetail;
