package com.ushirk.schools.repository;

import com.ushirk.schools.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
