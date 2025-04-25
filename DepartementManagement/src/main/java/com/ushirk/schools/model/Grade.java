package com.ushirk.schools.model;

import jakarta.persistence.*;
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
public class Grade {
    @Id
    @GeneratedValue
    private long gradeID;
    private String name  ;
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "grade")
    private List<Student> students;

}
