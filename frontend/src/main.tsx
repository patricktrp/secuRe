import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import Layout from "@/components/layout/layout";
import Projects from "@/pages/projects";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import ProjectDetail from "@/pages/project-detail";
import Recommender from "@/pages/recommender";
import NotFound from "@/pages/not-found";
import SecurityPatterns from "@/pages/security-patterns";
import SecurityPatternDetail from "./pages/security-pattern-detail";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 1000 * 60 * 5,
    },
  },
});

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        path: "/projects",
        element: <Projects />,
      },
      {
        path: "/projects/:projectId",
        element: <ProjectDetail />,
      },
      {
        path: "/recommender",
        element: <Recommender />,
      },
      {
        path: "/security-patterns",
        element: <SecurityPatterns />,
      },
      {
        path: "/security-patterns/:securityPatternId",
        element: <SecurityPatternDetail />,
      },
      {
        path: "*",
        element: <NotFound />,
      },
    ],
  },
]);

createRoot(document.getElementById("root")!).render(
  <QueryClientProvider client={queryClient}>
    <RouterProvider router={router} />
  </QueryClientProvider>
);
