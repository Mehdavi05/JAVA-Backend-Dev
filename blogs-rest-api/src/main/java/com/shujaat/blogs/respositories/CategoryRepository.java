package com.shujaat.blogs.respositories;

import com.shujaat.blogs.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
