import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Separator } from "@/components/ui/separator";
import { SidebarTrigger } from "@/components/ui/sidebar";
import {
  deleteSecurityRequirementById,
  getProjectById,
} from "@/services/api/projects";
import { useQuery } from "@tanstack/react-query";
import { NavLink } from "react-router-dom";
import { useParams } from "react-router-dom";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { MoreHorizontal } from "lucide-react";
import SecurityRequirementCreationDialog from "@/components/features/projects/security-requirement-creation-dialog";
import { useQueryClient } from "@tanstack/react-query";
import ProjectProperty from "@/components/features/projects/project-property";
import { Project } from "@/types/types";

const ProjectDetail = () => {
  const { projectId } = useParams();
  const queryClient = useQueryClient();

  const { data } = useQuery({
    queryKey: ["projects", projectId],
    queryFn: () => {
      if (projectId) {
        return getProjectById(parseInt(projectId));
      }
      throw new Error("Project ID is undefined");
    },
  });

  const handleSecurityRequirementDeletion = async (
    securityRequirementId: number
  ) => {
    if (projectId) {
      await deleteSecurityRequirementById(
        parseInt(projectId),
        securityRequirementId
      );
      queryClient.invalidateQueries({ queryKey: ["projects", projectId] });
    }
  };

  return (
    <div className="h-full">
      <header className="flex h-16 shrink-0 items-center gap-2">
        <div className="flex items-center gap-2 px-4">
          <SidebarTrigger className="-ml-1" />
          <Separator orientation="vertical" className="mr-2 h-4" />
          <Breadcrumb>
            <BreadcrumbList>
              <BreadcrumbItem className="hidden md:block">
                <NavLink to="/projects">
                  <BreadcrumbLink asChild>
                    <span>Projects</span>
                  </BreadcrumbLink>
                </NavLink>
              </BreadcrumbItem>
              <BreadcrumbSeparator className="hidden md:block" />
              <BreadcrumbItem>{data?.name}</BreadcrumbItem>
            </BreadcrumbList>
          </Breadcrumb>
        </div>
      </header>
      <main className="p-4 flex">
        <div className="w-2/3">
          <div className="flex justify-between items-center mb-2">
            <h3 className="font-medium">Security Requirements</h3>
            {projectId && (
              <SecurityRequirementCreationDialog projectId={projectId} />
            )}
          </div>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[100px]">Title</TableHead>
                <TableHead className="w-[200px]">Description</TableHead>
                <TableHead className="w-[30px]"></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {data?.securityRequirements.map((securityRequirement) => (
                <TableRow key={securityRequirement?.id}>
                  <TableCell className="text-xs font-medium">
                    {securityRequirement?.title}
                  </TableCell>
                  <TableCell className="font-medium text-xs">
                    {securityRequirement?.description}
                  </TableCell>
                  <TableCell>
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost" className="h-8 w-8 p-0">
                          <span className="sr-only">Open menu</span>
                          <MoreHorizontal className="h-4 w-4" />
                        </Button>
                      </DropdownMenuTrigger>
                      <DropdownMenuContent align="end">
                        <DropdownMenuItem>Open</DropdownMenuItem>
                        <DropdownMenuItem
                          onPointerDown={() =>
                            handleSecurityRequirementDeletion(
                              securityRequirement.id
                            )
                          }
                        >
                          Delete
                        </DropdownMenuItem>
                      </DropdownMenuContent>
                    </DropdownMenu>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </div>
        <div className="flex-1 px-5">
          <h3 className="font-semibold">Project Details</h3>
          {data && data.properties && (
            <div className="flex flex-col gap-y-3 mt-3">
              {Object.entries(data?.properties)?.map(([key, value]) => {
                if (!value) return;
                return (
                  <ProjectProperty
                    key={key}
                    propertyKey={key as keyof Project["properties"]}
                    propertyValue={value}
                  />
                );
              })}
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default ProjectDetail;
