package com.shujaat.blogs.payloads;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class PostResponse {
    @Schema(
            description = "Post Response content"
    )
    private List<PostDto> content;

    @Schema(
            description = "Post Response page number"
    )
    private int pageNo;

    @Schema(
            description = "Post Response page size"
    )
    private int pageSize;

    @Schema(
            description = "Post Response total elements"
    )
    private long totalElements;

    @Schema(
            description = "Post Response total pages"
    )
    private int totalPages;

    @Schema(
            description = "Post Response is last page"
    )
    private boolean last;

    public PostResponse() {
    }

    public PostResponse(List<PostDto> content, int pageNo, int pageSize, long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<PostDto> getContent() {
        return content;
    }

    public void setContent(List<PostDto> content) {
        this.content = content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
