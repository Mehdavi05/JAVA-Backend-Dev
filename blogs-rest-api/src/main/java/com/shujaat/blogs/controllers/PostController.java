package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.payloads.PostDtoV2;
import com.shujaat.blogs.payloads.PostResponse;
import com.shujaat.blogs.services.interfaces.IPostService;
import com.shujaat.blogs.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(
        name = "CRUD REST APIs for POST Resource"
)
public class PostController {
    private IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post REST API",
            description = "This API is used to save post to the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )

    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("api/v1/posts")
    public ResponseEntity<PostDto> createPostV1(@Valid @RequestBody PostDto post){
        PostDto postRes = postService.createPost(post);
        return new ResponseEntity<>(postRes, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Create Post REST API",
            description = "This API is used to save post to the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )

    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("api/v2/posts")
    public ResponseEntity<PostDtoV2> createPostV2(@Valid @RequestBody PostDto post){
        PostDto dtoV1 = postService.createPost(post);

        PostDtoV2 dtoV2 = new PostDtoV2();
        dtoV2.setId(dtoV1.getId());
        dtoV2.setTitle(dtoV1.getTitle());
        dtoV2.setDescription(dtoV1.getDescription());
        dtoV2.setContent(dtoV1.getContent());
        dtoV2.setComments(dtoV1.getComments());
        dtoV2.setCategoryId(dtoV1.getCategoryId());

        List<String> tags = new ArrayList<>();
        tags.add("JAVA");
        tags.add("Spring");
        tags.add("Spring Boot");

        dtoV2.setTags(tags);

        return new ResponseEntity<>(dtoV2, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Posts REST API",
            description = "This API is used to fetch all Posts from the database with pagination Applied as pageNo, pageSize, sortBy, sortDir"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("api/v1/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Get POST(post id provided) REST API",
            description = "This API is used to Get post from the database provided it's id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostByV1(@PathVariable(name = "id") long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.FOUND);
    }

    @Operation(
            summary = "Get POST(post id provided) REST API",
            description = "This API is used to Get post from the database provided it's id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("api/v2/posts/{id}")
    public ResponseEntity<PostDtoV2> getPostByV2(@PathVariable(name = "id") long id){
        PostDto dtoV1 = postService.getPostById(id);

        PostDtoV2 dtoV2 = new PostDtoV2();
        dtoV2.setId(dtoV1.getId());
        dtoV2.setTitle(dtoV1.getTitle());
        dtoV2.setDescription(dtoV1.getDescription());
        dtoV2.setContent(dtoV1.getContent());
        dtoV2.setComments(dtoV1.getComments());
        dtoV2.setCategoryId(dtoV1.getCategoryId());

        List<String> tags = new ArrayList<>();
        tags.add("JAVA");
        tags.add("Spring");
        tags.add("Spring Boot");

        dtoV2.setTags(tags);

        return new ResponseEntity<>(dtoV2, HttpStatus.FOUND);
    }

    @Operation(
            summary = "Update Post REST API",
            description = "This API is used to update post to the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable long id){
        PostDto dto = postService.updatePost(postDto, id);
        return  new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Post REST API",
            description = "This API is used to delete post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post with the given id deleted successfully", HttpStatus.OK);
    }

    @Operation(
            summary = "Get Posts for the Category Id REST API",
            description = "This API is used to get posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("api/v1/posts/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name = "id") long categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
