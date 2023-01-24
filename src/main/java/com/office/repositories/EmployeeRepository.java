package com.office.repositories;

import com.office.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByDisabled(Boolean disabled);

    @Query(value = "SELECT e.* FROM employee e INNER JOIN work_position w ON e.work_position_id = w.id WHERE w.id = ?;", nativeQuery = true)
    List<Employee> findByWorkPosition(Long workPositionId);
}
