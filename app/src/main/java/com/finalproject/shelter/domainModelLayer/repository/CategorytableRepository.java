package com.finalproject.shelter.domainModelLayer.repository;

import com.finalproject.shelter.domainModelLayer.model.entity.noticationDomain.Categorytable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorytableRepository extends JpaRepository<Categorytable,Long> {

}
