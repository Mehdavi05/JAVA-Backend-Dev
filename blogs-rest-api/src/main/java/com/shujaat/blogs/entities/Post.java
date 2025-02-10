package com.shujaat.blogs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/*
 * Lombok annotations to create properties setters and constructors
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "posts", uniqueConstraints= {@UniqueConstraint(columnNames= {"title"})})
public class Post {
	@Id
	@GeneratedValue(
			strategy = GenerationType.IDENTITY
			)
	private long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "content", nullable = false)
	private String content;
}
