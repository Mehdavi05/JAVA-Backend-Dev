package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.CommentDto;
import com.shujaat.blogs.services.interfaces.ICommentService;
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
}
