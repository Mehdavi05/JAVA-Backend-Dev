package com.shujaat.blogs.payloads;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Schema(
        description = "PostDto model information"
)
public class PostDto {
    private long id;

    @Schema(
            description = "Blog post title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(
            description = "Blog post description"
    )
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @Schema(
            description = "Blog post content"
    )
    @NotEmpty
    private String content;

    @Schema(
            description = "Blog post category id"
    )
    private long categoryId;

    @Schema(
            description = "Blog post post comments"
    )
    private Set<CommentDto> comments;

    public PostDto() {
    }

    public PostDto(long id, String title, String description, String content, Set<CommentDto> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.comments = comments;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDto> comments) {
        this.comments = comments;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
