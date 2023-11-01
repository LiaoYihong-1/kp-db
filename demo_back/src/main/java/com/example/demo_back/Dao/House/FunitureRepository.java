package com.example.demo_back.dao.house;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FunitureRepository extends JpaRepository<FurnitureJpa,Integer> {
    @Query(value="select F from FurnitureJpa F where F.roomId = ?1")
    List<FurnitureJpa> findAllByRoom(Integer id);

    @Query(value="select F from  FurnitureJpa F where F.id = ?1")
    List<FurnitureJpa> findFurnitureJpaById(Integer id);

}
