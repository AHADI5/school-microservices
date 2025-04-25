package com.ushirk.schools.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Department {
    @Id
    @GeneratedValue
    private  long departmentID  ;
    private String departmentName ;
    @OneToMany(mappedBy = "department")
    List<Grade> grades ;

}
