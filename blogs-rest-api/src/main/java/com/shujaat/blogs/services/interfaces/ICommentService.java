package com.shujaat.blogs.services.interfaces;

import com.shujaat.blogs.payloads.CommentDto;
import com.shujaat.blogs.payloads.CommentResponse;
import com.shujaat.blogs.payloads.PostDto;
import com.shujaat.blogs.payloads.PostResponse;

public interface ICommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    CommentResponse getAllComments(long postId, int pageNo, int pageSize, String sortBy, String sortDir);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

}
