package com.test.animal.repository;

import com.test.animal.entity.Care;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface CareRepository extends JpaRepository<Care, Long> {
}
