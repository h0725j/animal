package com.test.animal.repository;

import com.test.animal.entity.Shelter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    List<Shelter> findByCareNm(String careNm);
    List<Shelter> findByCareRegNo(String careRegNo);
}
