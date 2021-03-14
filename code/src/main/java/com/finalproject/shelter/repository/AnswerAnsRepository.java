package com.finalproject.shelter.repository;

import com.finalproject.shelter.model.entity.AnswerAns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerAnsRepository extends JpaRepository<AnswerAns,Long> {
}
