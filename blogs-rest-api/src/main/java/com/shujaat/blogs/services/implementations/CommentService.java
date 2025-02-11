package com.shujaat.blogs.services.implementations;

import com.shujaat.blogs.entities.Comment;
import com.shujaat.blogs.entities.Post;
import com.shujaat.blogs.exceptions.CommentAPIException;
import com.shujaat.blogs.exceptions.ResourceNotFoundException;
import com.shujaat.blogs.payloads.CommentDto;
import com.shujaat.blogs.payloads.CommentResponse;
import com.shujaat.blogs.respositories.CommentRepository;
import com.shujaat.blogs.respositories.PostsRepository;
import com.shujaat.blogs.services.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {
    private CommentRepository commentRepository;
    private PostsRepository postsRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostsRepository postsRepository) {
        this.commentRepository = commentRepository;
        this.postsRepository = postsRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        //Map CommentDto to Comment
        Comment comment = mapToEntity(commentDto);

        //Fetch Post
        Post post = postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);

        //Save Comment
        Comment createdComment = commentRepository.save(comment);

        //Change created comment to CommentDto
        CommentDto commentResponse = mapToDto(createdComment);

        return commentResponse;
    }

    @Override
    public CommentResponse getAllComments(long postId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);

        //Get content for Page object
        List<Comment> commentsList = comments.getContent();

        List<CommentDto> content = commentsList.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(content);
        commentResponse.setPageNo(comments.getNumber());
        commentResponse.setPageSize(comments.getSize());
        commentResponse.setTotalElements(comments.getTotalElements());
        commentResponse.setTotalPages(comments.getTotalPages());
        commentResponse.setLast(comments.isLast());

        return commentResponse;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId() != post.getId()){
            throw new CommentAPIException(HttpStatus.BAD_REQUEST, String.format("Comment with provided id: %s doesn't belong to the post with id: %s", commentId, postId));
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId() != post.getId()){
            throw new CommentAPIException(HttpStatus.BAD_REQUEST, String.format("Comment with provided id: %s doesn't belong to the post with id: %s", commentId, postId));
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId() != post.getId()){
            throw new CommentAPIException(HttpStatus.BAD_REQUEST, String.format("Comment with provided id: %s doesn't belong to the post with id: %s", commentId, postId));
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }
}
