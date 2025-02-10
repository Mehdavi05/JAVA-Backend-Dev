package com.shujaat.blogs.services.implementations;

import com.shujaat.blogs.entities.Post;
import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.respositories.PostsRepository;
import com.shujaat.blogs.services.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService  implements IPostService {
    private PostsRepository postsRepository;

    @Autowired //This annotation can be removed if the class is having only one constructor
    public PostService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post createdPost = postsRepository.save(post);

        PostDto postResponse = new PostDto();
        postResponse.setId(createdPost.getId());
        postResponse.setTitle(createdPost.getTitle());
        postResponse.setDescription(createdPost.getDescription());
        postResponse.setContent(createdPost.getContent());


        return postResponse;
    }
}
