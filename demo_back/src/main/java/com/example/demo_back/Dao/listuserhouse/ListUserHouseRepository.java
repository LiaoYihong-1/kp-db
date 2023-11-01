package com.example.demo_back.dao.listuserhouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListUserHouseRepository extends JpaRepository<ListUserHouseJpa, Integer> {

}
