package com.example.darybadyplomwork.repos;

import com.example.darybadyplomwork.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    @Query("""
            select c from City c
            where upper(c.enName) like upper(concat('%', ?1, '%')) or upper(c.ukName) like upper(concat('%', ?2, '%'))""")
    List<City> findCitiesByEnNameContainingIgnoreCaseOrUkNameContainingIgnoreCase(String enName,String ukName);
}