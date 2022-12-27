package com.example.darybadyplomwork.repos;

import com.example.darybadyplomwork.entity.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidenceRepository extends JpaRepository<Residence,Long> {

}
