package com.shujaat.blogs.payloads;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CategoryDtoV2 {
    private long id;

    @Schema(
            description = "Blog category name"
    )
    private String name;

    @Schema(
            description = "Blog category description"
    )
    private String description;

    private List<String> tags;

    public CategoryDtoV2() {
    }

    public CategoryDtoV2(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
