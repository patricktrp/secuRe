"use client";
import { ColumnDef } from "@tanstack/react-table";
import { MoreHorizontal, ArrowUpDown } from "lucide-react";

import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { NavLink } from "react-router-dom";
import { SecurityPattern } from "@/types/types";

export const columns: ColumnDef<SecurityPattern>[] = [
  {
    accessorKey: "name",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Name
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      );
    },
    cell: ({ row }) => {
      const securityPattern = row.original;
      return <span className="w-[2000px]">{securityPattern.name}</span>;
    },
  },
  {
    accessorKey: "securityControl",
    header: "Security Control",
    cell: ({ row }) => {
      const securityPattern = row.original;
      return <span>{securityPattern.securityControl.type}</span>;
    },
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const securityPattern = row.original;

      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <span className="sr-only">Open menu</span>
              <MoreHorizontal className="h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuItem>
              <NavLink
                className="w-full h-full"
                to={`/security-patterns/${securityPattern.id}`}
              >
                Open
              </NavLink>
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      );
    },
  },
];
