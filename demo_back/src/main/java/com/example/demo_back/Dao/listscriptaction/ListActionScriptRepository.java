package com.example.demo_back.dao.listscriptaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListActionScriptRepository extends JpaRepository<ListActionScript,Integer> {
    @Query(value ="select L from ListActionScript L where L.script_id = ?1")
    List<ListActionScript> findbyscript(Integer id);
}
