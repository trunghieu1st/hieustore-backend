package com.example.hieustore.repository;

import com.example.hieustore.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query(value = "SELECT * FROM categories WHERE name = ?1", nativeQuery = true)
    Category findByCategoryName(String name);
}
