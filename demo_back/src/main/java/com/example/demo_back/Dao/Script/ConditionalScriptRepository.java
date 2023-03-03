package com.example.demo_back.Dao.Script;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConditionalScriptRepository extends JpaRepository<ConditionalScript,Integer> {
    @Query(value="select C from ConditionalScript C where C.script_id =?1 ")
    List<ConditionalScript> findbyscriptid(Integer id);
}
