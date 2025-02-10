package com.shujaat.blogs.services.interfaces;

import com.shujaat.blogs.payloads.PostDto;

import java.util.List;

public interface IPostService {
    PostDto createPost(PostDto post);
    List<PostDto> getAllPosts();
}
