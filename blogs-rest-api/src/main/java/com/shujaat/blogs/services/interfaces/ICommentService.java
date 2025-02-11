package com.shujaat.blogs.services.interfaces;

import com.shujaat.blogs.payloads.CommentDto;

public interface ICommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

}
