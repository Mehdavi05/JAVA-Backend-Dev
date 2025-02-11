package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.CommentDto;
import com.shujaat.blogs.payloads.CommentResponse;
import com.shujaat.blogs.payloads.PostResponse;
import com.shujaat.blogs.services.interfaces.ICommentService;
import com.shujaat.blogs.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
public class CommentController {
    private ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("{id}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "id") long postId, @RequestBody CommentDto commentDto){
        CommentDto commentResponse = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}/comment")
    public ResponseEntity<CommentResponse> getAllPosts(
            @PathVariable(name = "id") long postId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        CommentResponse commentResponse = commentService.getAllComments(postId, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }
}
