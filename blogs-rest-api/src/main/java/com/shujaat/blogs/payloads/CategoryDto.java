package com.shujaat.blogs.payloads;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryDto {
    private long id;

    @Schema(
            description = "Blog category name"
    )
    private String name;

    @Schema(
            description = "Blog category description"
    )
    private String description;

    public CategoryDto() {
    }

    public CategoryDto(long id, String name, String description) {
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
}
