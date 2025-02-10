package com.shujaat.blogs.services.interfaces;

import com.shujaat.blogs.payloads.PostDto;

public interface IPostService {
    PostDto createPost(PostDto post);
}
