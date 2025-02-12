package com.shujaat.blogs.services.implementations;

import com.shujaat.blogs.entities.Category;
import com.shujaat.blogs.entities.Post;
import com.shujaat.blogs.exceptions.ResourceNotFoundException;
import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.payloads.PostResponse;
import com.shujaat.blogs.respositories.CategoryRepository;
import com.shujaat.blogs.respositories.PostsRepository;
import com.shujaat.blogs.services.interfaces.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService  implements IPostService {
    private PostsRepository postsRepository;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;

    @Autowired //This annotation can be removed if the class is having only one constructor
    public PostService(PostsRepository postsRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.postsRepository = postsRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post createdPost = postsRepository.save(post);

        PostDto postRes = mapToDto(createdPost);
        return postRes;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postsRepository.findAll(pageable);

        //Get content for Page object
        List<Post> postsList = posts.getContent();

        List<PostDto> content = postsList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postsRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postsRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Post> posts = postsRepository.findByCategoryId(categoryId);

        return posts.stream().map((post) -> mapToDto(post))
                .collect(Collectors.toList());
    }

    private PostDto mapToDto(Post createdPost)
    {
        PostDto postResponse = modelMapper.map(createdPost, PostDto.class);
        return  postResponse;
    }

    private  Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
}
