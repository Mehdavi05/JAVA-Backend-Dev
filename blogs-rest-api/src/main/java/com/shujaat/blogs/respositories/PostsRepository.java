package com.shujaat.blogs.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shujaat.blogs.entities.Post;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long id);
}
