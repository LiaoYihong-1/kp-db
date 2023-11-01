package com.example.demo_back.dao.problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem,Integer> {
    @Query(value="select P from Problem P where P.userId=?1")
    List<Problem> findAllByUserId(Integer id);
}
