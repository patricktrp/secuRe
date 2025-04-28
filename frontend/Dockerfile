# Stage 1: Build React App
FROM node:lts-alpine AS build

WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy the source code and build the app
COPY . .
RUN npm run build

# Stage 2: Serve with Nginx
FROM nginx:stable-alpine

# Copy build output to Nginx's html directory
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 5173
EXPOSE 5173

CMD ["nginx", "-g", "daemon off;"]