package com.ushirk.schools.services;

import com.ushirk.schools.dtos.DepartmentRequest;
import com.ushirk.schools.model.Department;
import com.ushirk.schools.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public record DepartementService(
        DepartmentRepository departmentRepository
) {
    public Department registerDepartment(DepartmentRequest request) {
        Department department = Department.builder()
                .departmentName(request.departmentName())
                .build();
        return departmentRepository.save(department);
    }
}
