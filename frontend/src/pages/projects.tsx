import { deleteProjectById, getProjects } from "@/services/api/projects";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
} from "@/components/ui/breadcrumb";
import { Separator } from "@/components/ui/separator";

import { SidebarTrigger } from "@/components/ui/sidebar";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { NavLink } from "react-router-dom";
import { Skeleton } from "@/components/ui/skeleton";
import { Button } from "@/components/ui/button";
import { Plus, ExternalLink, Trash2, Ellipsis } from "lucide-react";
import ProjectCreationFormDialog from "@/components/features/projects/project-creation-form/project-creation-form-dialog";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuShortcut,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useNavigate } from "react-router-dom";

const Projects = () => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();

  const { data: projects, isLoading: projectsIsLoading } = useQuery({
    queryKey: ["projects"],
    queryFn: getProjects,
  });

  const { mutate: deleteProject } = useMutation({
    mutationFn: (projectId: number) => deleteProjectById(projectId),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["projects"] }),
  });

  return (
    <div className="relative h-full">
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
            </BreadcrumbList>
          </Breadcrumb>
        </div>
      </header>
      <main className="px-4">
        <AlertDialog>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Name</TableHead>
                <TableHead>Created At</TableHead>
                <TableHead>Updated At</TableHead>
                <TableHead></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {projectsIsLoading &&
                [0, 1, 2, 3, 4].map((num) => (
                  <TableRow key={num}>
                    <TableCell>
                      <Skeleton className="h-4 my-1 rounded-full" />
                    </TableCell>
                    <TableCell>
                      <Skeleton className="h-4 my-1 rounded-full" />
                    </TableCell>
                    <TableCell>
                      <Skeleton className="h-4 my-1 rounded-full" />
                    </TableCell>
                  </TableRow>
                ))}
              {projects?.map((project) => (
                <TableRow key={project.id}>
                  <TableCell className="font-medium">{project.name}</TableCell>
                  <TableCell>
                    {new Date(project.createdAt).toLocaleDateString()}
                  </TableCell>
                  <TableCell>
                    {new Date(project.updatedAt).toLocaleDateString()}
                  </TableCell>
                  <TableCell>
                    <DropdownMenu>
                      <DropdownMenuTrigger asChild>
                        <Button variant="ghost" className="w-4 h-6">
                          <Ellipsis className="size-4 text-muted-foreground" />
                        </Button>
                      </DropdownMenuTrigger>
                      <DropdownMenuContent>
                        <DropdownMenuGroup>
                          <DropdownMenuItem
                            onPointerDown={() =>
                              navigate(`/projects/${project.id}`)
                            }
                          >
                            Open
                            <DropdownMenuShortcut>
                              <ExternalLink className="size-4" />
                            </DropdownMenuShortcut>
                          </DropdownMenuItem>

                          <AlertDialogTrigger asChild>
                            <DropdownMenuItem>
                              Delete
                              <DropdownMenuShortcut>
                                <Trash2 className="size-4" />
                              </DropdownMenuShortcut>
                            </DropdownMenuItem>
                          </AlertDialogTrigger>
                        </DropdownMenuGroup>
                      </DropdownMenuContent>
                    </DropdownMenu>
                  </TableCell>
                  <AlertDialogContent>
                    <AlertDialogHeader>
                      <AlertDialogTitle>
                        Are you absolutely sure?
                      </AlertDialogTitle>
                      <AlertDialogDescription>
                        This action cannot be undone. This will permanently
                        delete your project and all associated projects.
                      </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                      <AlertDialogAction
                        onClick={() => deleteProject(project.id)}
                      >
                        Delete
                      </AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </AlertDialog>
      </main>

      <ProjectCreationFormDialog
        trigger={
          <Button className="rounded-full size-12 absolute bottom-5 right-5">
            <Plus />
          </Button>
        }
      />
    </div>
  );
};

export default Projects;
