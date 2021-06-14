package com.finalproject.shelter.domain.repository;

import com.finalproject.shelter.domain.model.entity.noticationDomain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Optional<Board> findBoardById(@Param("id") Long id);
    Page<Board> findBoardByCategoryIdAndTitleContainingAndContentsContainingOrderByRegisteredAtDescIdDesc(Long id,String title,String content,Pageable pageable);
    List<Board> findBoardByCategoryId(Long id);
    List<Board> findTop5ByCategoryIdAndRegisteredAtBetweenOrderByViewBoardDesc(Long id, LocalDate date1, LocalDate date2);
    Optional<Board> findBoardByTitle(String title);
}
