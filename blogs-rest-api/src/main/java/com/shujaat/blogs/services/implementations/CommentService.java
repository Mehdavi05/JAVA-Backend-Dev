package com.shujaat.blogs.services.implementations;

import com.shujaat.blogs.entities.Comment;
import com.shujaat.blogs.entities.Post;
import com.shujaat.blogs.exceptions.ResourceNotFoundException;
import com.shujaat.blogs.payloads.CommentDto;
import com.shujaat.blogs.respositories.CommentRepository;
import com.shujaat.blogs.respositories.PostsRepository;
import com.shujaat.blogs.services.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
