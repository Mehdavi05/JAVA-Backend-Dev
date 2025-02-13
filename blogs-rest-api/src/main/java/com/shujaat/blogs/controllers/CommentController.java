package com.shujaat.blogs.controllers;

import com.shujaat.blogs.payloads.CommentDto;
import com.shujaat.blogs.payloads.CommentResponse;
import com.shujaat.blogs.services.interfaces.ICommentService;
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

@RestController
@Tag(
        name = "CRUD REST APIs for Comment Resource"
)
public class CommentController {
    private ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Create Comment REST API",
            description = "This API is used to save comment to the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("api/v1/posts/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, @Valid @RequestBody CommentDto commentDto){
        CommentDto commentResponse = commentService.createComment(postId, commentDto);

        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Comments REST API",
            description = "This API is used to get comments from the database with pagination and sorting applied"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("api/v1/posts/{postId}/comment")
    public ResponseEntity<CommentResponse> getAllComments(
            @PathVariable(name = "postId") long postId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        CommentResponse commentResponse = commentService.getAllComments(postId, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Comment for the provided id REST API",
            description = "This API is used to get comments from the database with the given id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )

    @GetMapping("api/v1/posts/{postId}/comment/{id}")
    public ResponseEntity<CommentDto> getCommentV1(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "id") long commentId) {
        CommentDto comment = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("api/v2/posts/{postId}/comment/{id}")
    public ResponseEntity<CommentDto> getCommentV2(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "id") long commentId) {
        CommentDto comment = commentService.getCommentById(postId, commentId);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Comment REST API",
            description = "This API is used to update comment in the database with the given id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("api/v1/posts/{postId}/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "id") long commentId,
            @Valid @RequestBody CommentDto commentDto) {
        CommentDto comment = commentService.updateComment(postId, commentId, commentDto);

        return  new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment REST API",
            description = "This API is used to delete comment from the database with the given id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Bearer Token"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("api/v1/posts/{postId}/comment/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "id") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(String.format("Comment with the given comment id: %s deleted successfully", commentId), HttpStatus.OK);
    }
}
