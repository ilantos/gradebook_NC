package lab3.gradebook.nc.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class Lesson {
    private int id;
    private String title;
    private String description;
    private Subject subject;
    private float maxGrade;
    private LocalDateTime creationDate;
}
