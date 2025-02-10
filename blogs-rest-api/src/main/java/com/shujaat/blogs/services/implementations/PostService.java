package com.shujaat.blogs.services.implementations;

import com.shujaat.blogs.entities.Post;
import com.shujaat.blogs.exceptions.ResourceNotFoundException;
import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.respositories.PostsRepository;
import com.shujaat.blogs.services.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService  implements IPostService {
    private PostsRepository postsRepository;

    @Autowired //This annotation can be removed if the class is having only one constructor
    public PostService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post createdPost = postsRepository.save(post);
        PostDto postRes = mapToDto(createdPost);
        return postRes;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postsRepository.findAll();
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postsRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postsRepository.delete(post);
    }

    private PostDto mapToDto(Post createdPost)
    {
        PostDto postResponse = new PostDto();
        postResponse.setId(createdPost.getId());
        postResponse.setTitle(createdPost.getTitle());
        postResponse.setDescription(createdPost.getDescription());
        postResponse.setContent(createdPost.getContent());

        return  postResponse;
    }

    private  Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }
}
