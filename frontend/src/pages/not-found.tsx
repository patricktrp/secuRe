import { SquareSlash } from "lucide-react";

const NotFound = () => {
  return (
    <div className="w-full h-full flex justify-center items-center">
      <div className="flex flex-col items-center gap-y-2 text-muted-foreground">
        <SquareSlash />
        <div>404 - Not Found</div>
      </div>
    </div>
  );
};

export default NotFound;
