package lab3.gradebook.nc.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EditGradeRequest {
    private int idLesson;
    private int idPerson;
    private double grade;
}
