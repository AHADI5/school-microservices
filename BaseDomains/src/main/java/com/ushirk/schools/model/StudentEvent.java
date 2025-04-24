package com.ushirk.schools.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEvent {
    private long matricule;
    private long gradeID ;

}
