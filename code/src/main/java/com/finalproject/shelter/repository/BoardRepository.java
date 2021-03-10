package com.finalproject.shelter.repository;

import com.finalproject.shelter.model.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    Optional<Board> findBoardById(@Param("id") Long id);

    Page<Board> findBoardByCategoryIdAndTitleContainingAndContentsContaining(@Param("id") Long id,String title,String content,Pageable pageable);

    List<Board> findBoardByCategoryIdAndTitleContainingAndContentsContaining(Long id,String title,String content);

    List<Board> findBoardByCategoryId(Long id);

}
