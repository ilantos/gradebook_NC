package lab3.gradebook.nc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Grade {
    private Person person;
    private Subject subject;
    private Lesson lesson;
    private double grade;

}
