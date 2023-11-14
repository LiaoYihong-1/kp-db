package com.example.demo_back.dao.listuserhouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListUserHouseRepository extends JpaRepository<ListUserHouseJpa, Integer> {
    @Query(value="select L from ListUserHouseJpa L where L.house_id = ?1 and L.user_id = ?2")
    ListUserHouseJpa findByUserAndHouse(Integer house, Integer user);
}
