package com.cs.springbootjavafx.repository;

import com.cs.springbootjavafx.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findCategoryByName(String Name);
}