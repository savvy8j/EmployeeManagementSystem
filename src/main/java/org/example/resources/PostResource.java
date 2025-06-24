package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.api.PostDTO;
import org.example.client.PostClient;

import static org.eclipse.jetty.http.HttpURI.build;

@Path("/api/client/posts")

public class PostResource {
    private final PostClient postClient;

    public PostResource(PostClient postClient) {
        this.postClient = postClient;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPosts() {
        return Response.status(Response.Status.OK)
                .entity(postClient.getAllPosts())
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostsById(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK)
                .entity(postClient.getPost(id))
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPost(PostDTO postDTO) {
        return Response.status(Response.Status.OK)
                .entity(postClient.createPost(postDTO))
                .build();
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePosts(PostDTO postDTO , @PathParam("id") int id) {
        return Response.status(Response.Status.OK)
                .entity(postClient.updatePost(postDTO,id))
                .build();

    }

    @Path("/{id}")
    @DELETE
    public Response deletePost(@PathParam("id") Integer id) {
        postClient.deletePost(id);
        return Response.status(Response.Status.NO_CONTENT)
                .build();
    }
}
