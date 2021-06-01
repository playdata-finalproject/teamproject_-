package com.finalproject.shelter.domain.repository;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Categorytable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorytableRepository extends JpaRepository<Categorytable,Long> {
}
