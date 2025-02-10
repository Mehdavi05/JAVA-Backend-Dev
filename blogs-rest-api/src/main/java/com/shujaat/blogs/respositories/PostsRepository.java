package com.shujaat.blogs.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shujaat.blogs.entities.Post;

public interface PostsRepository extends JpaRepository<Post, Long> {

}
