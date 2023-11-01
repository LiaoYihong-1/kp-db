package com.example.demo_back.dao.action;

import com.example.demo_back.dao.enums.ActionType;
import com.example.demo_back.dao.enums.FurnitureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionRepository extends JpaRepository<ActionJpa,Integer> {
    @Query(value = "select A from ActionJpa A where A.action = ?1 and A.furniture=?2")
    List<ActionJpa> findAllByActionAndFurnitrue(ActionType a, FurnitureType f);

    @Query(value = "select A from ActionJpa A where A.id = ?1")
    List<ActionJpa> findActionById(Integer id);
}

