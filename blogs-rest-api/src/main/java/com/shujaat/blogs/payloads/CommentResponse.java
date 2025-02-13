package com.shujaat.blogs.payloads;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CommentResponse {
    @Schema(
            description = "Comment response content"
    )
    private List<CommentDto> content;

    @Schema(
            description = "Comment response page number"
    )
    private int pageNo;

    @Schema(
            description = "Comment response page size"
    )
    private int pageSize;

    @Schema(
            description = "Comment response total elements"
    )
    private long totalElements;

    @Schema(
            description = "Comment response total pages"
    )
    private int totalPages;

    @Schema(
            description = "Comment response is last page"
    )
    private boolean last;

    public CommentResponse() {
    }

    public CommentResponse(List<CommentDto> content, int pageNo, int pageSize, long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<CommentDto> getContent() {
        return content;
    }

    public void setContent(List<CommentDto> content) {
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
