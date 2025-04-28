import { getSecurityPatterns } from "@/services/api/security-patterns";
import { useQuery } from "@tanstack/react-query";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
} from "@/components/ui/breadcrumb";
import { Separator } from "@/components/ui/separator";
import { SidebarTrigger } from "@/components/ui/sidebar";
import { NavLink } from "react-router-dom";
import { columns } from "@/components/features/security-patterns/security-pattern-table-columns";
import { SecurityPatternTable } from "@/components/features/security-patterns/security-pattern-table";

const SecurityPatterns = () => {
  const { data, isLoading } = useQuery({
    queryKey: ["security-patterns"],
    queryFn: getSecurityPatterns,
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
            </BreadcrumbList>
          </Breadcrumb>
        </div>
      </header>

      <main className="px-4">
        {data && !isLoading && (
          <SecurityPatternTable columns={columns} data={data} />
        )}
      </main>
    </div>
  );
};

export default SecurityPatterns;
