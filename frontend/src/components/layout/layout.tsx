import { AppSidebar } from "@/components/navigation/app-sidebar";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
import { useEffect } from "react";
import { Outlet, useLocation, useNavigate } from "react-router-dom";

const Layout = () => {
  const location = useLocation();
  const navigate = useNavigate()

  useEffect(() => {
    if (location.pathname === "/") {
      navigate('/projects')
    }
  }, [location, navigate]);

  return (
    <SidebarProvider>
      <AppSidebar />
      <SidebarInset>
        <Outlet />
      </SidebarInset>
    </SidebarProvider>
  );
};

export default Layout;
