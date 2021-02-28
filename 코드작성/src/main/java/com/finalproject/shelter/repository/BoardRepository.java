package com.finalproject.shelter.repository;

import com.finalproject.shelter.model.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findAllByCategoryId(Long id);
}
