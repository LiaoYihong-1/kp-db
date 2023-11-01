package com.example.demo_back.dao.script;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScriptRepository extends JpaRepository<Script,Integer> {
    @Query(value="select max(id) from Script")
    List<Integer> findNewestScript();

}
