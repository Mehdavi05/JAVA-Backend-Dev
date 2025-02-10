package com.shujaat.blogs.payloads;

import lombok.Data;

/*
@Data lombok annotation creates -> : Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode, Value, automatically
 */
@Data
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
}
