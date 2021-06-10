package com.finalproject.shelter.domain.repository;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findCategoryByCategorytableId(Long id);
    Category findById(String id);
}
