package com.finalproject.shelter.domainModelLayer.repository;

import com.finalproject.shelter.domainModelLayer.model.entity.noticationDomain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findCategoryByCategorytableId(Long id);

    List<Category> findAllByCategorytableId(Long id);

}
