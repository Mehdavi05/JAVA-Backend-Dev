package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.CommentDto;
import com.shujaat.blogs.payloads.CommentResponse;
import com.shujaat.blogs.payloads.PostResponse;
import com.shujaat.blogs.services.interfaces.ICommentService;
import com.shujaat.blogs.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
public class CommentController {
    private ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, @Valid @RequestBody CommentDto commentDto){
        CommentDto commentResponse = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("{postId}/comment")
    public ResponseEntity<CommentResponse> getAllComments(
            @PathVariable(name = "postId") long postId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        CommentResponse commentResponse = commentService.getAllComments(postId, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @GetMapping("{postId}/comment/{id}")
    public ResponseEntity<CommentDto> getComment(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "id") long commentId) {
        CommentDto comment = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{postId}/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "id") long commentId,
            @Valid @RequestBody CommentDto commentDto) {
        CommentDto comment = commentService.updateComment(postId, commentId, commentDto);

        return  new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{postId}/comment/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "id") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(String.format("Comment with the given comment id: %s deleted successfully", commentId), HttpStatus.OK);
    }
}
