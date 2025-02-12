package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.payloads.PostResponse;
import com.shujaat.blogs.services.interfaces.IPostService;
import com.shujaat.blogs.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto post){
        PostDto postRes = postService.createPost(post);
        return new ResponseEntity<>(postRes, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostBy(@PathVariable(name = "id") long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable long id){
        PostDto dto = postService.updatePost(postDto, id);
        return  new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post with the given id deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id") long categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
