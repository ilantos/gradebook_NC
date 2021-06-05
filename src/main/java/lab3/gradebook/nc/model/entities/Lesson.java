package lab3.gradebook.nc.model.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {
    private int id;
    private String title;
    private String description;
    private float maxGrade;
    private LocalDateTime creationDate;
    private LocalDateTime startDate;
}
