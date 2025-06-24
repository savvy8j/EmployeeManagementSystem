package org.example.client;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.api.PostDTO;
import org.glassfish.jersey.http.ResponseStatus;

import java.util.List;

public class PostClient {
    private final Client client;
    private final String postsUrl;

    public PostClient(Client client, String postsUrl) {
        this.client = client;
        this.postsUrl = postsUrl;
    }

    public List<PostDTO> getAllPosts(){
        List<PostDTO> postDTOS = client.target(postsUrl)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<PostDTO>>() {
                });

        return postDTOS;
    }

    public PostDTO getPost(Integer postId){
        return client.target(postsUrl + "/" + postId)
                .request(MediaType.APPLICATION_JSON)
                .get(PostDTO.class);
    }

    public PostDTO createPost(PostDTO postDTO){
        return client.target(postsUrl + "/add")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(postDTO, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(PostDTO.class);

    }


    public PostDTO updatePost(PostDTO postDTO,int id) {
        return client.target(postsUrl+"/"+id)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(postDTO, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(PostDTO.class);
    }

    public void deletePost(Integer postId){
        Response deleteResponse = client.target(postsUrl + "/" + postId)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        int status = deleteResponse.getStatus();
        System.out.println(status);
    }

}
