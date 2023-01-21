package com.office.repositories;

import com.office.entities.WorkPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPositionRepository extends JpaRepository<WorkPosition, Long> {
}
