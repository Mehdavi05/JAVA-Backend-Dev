package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.services.interfaces.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto post){
        PostDto postRes = postService.createPost(post);
        return new ResponseEntity<>(postRes, HttpStatus.CREATED);
    }
}
