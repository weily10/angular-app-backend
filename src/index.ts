import { DurableObject } from "cloudflare:workers";

// 1. Export the Durable Object class that Cloudflare links to your container
export class BackendContainer extends DurableObject {
  async fetch(request: Request): Promise<Response> {
    // Port 8080 should match EXPOSE 8080 in your Spring Boot Dockerfile
    return await containerFetch(request, 8080);
  }
}

// 2. Main HTTP Gateway to route incoming traffic into the container
export default {
  async fetch(request: Request, env: any): Promise<Response> {
    const id = env.BACKEND_CONTAINER.idFromName("spring-boot-instance");
    const container = env.BACKEND_CONTAINER.get(id);
    return await container.fetch(request);
  },
};