package com.shujaat.blogs.services.interfaces;

import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.payloads.PostResponse;

import java.util.List;

public interface IPostService {
    PostDto createPost(PostDto post);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto post, long id);
    void deletePost(long id);
}
