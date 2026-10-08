package com.employee.backend.repository;

import com.employee.backend.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

    boolean existsByUnitId(Long unitId);

    @Query("""
        SELECT e FROM Employee e
        WHERE
            (
                :search = ''
                OR LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(e.address) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(e.phone) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(e.username) LIKE LOWER(CONCAT('%', :search, '%'))
            )
            AND
            (
                :role = ''
                OR e.role = :role
            )
        """)
    Page<Employee> searchEmployees(
            @Param("search") String search,
            @Param("role") String role,
            Pageable pageable
    );
}