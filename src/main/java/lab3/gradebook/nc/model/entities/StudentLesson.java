package lab3.gradebook.nc.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class StudentLesson extends Lesson {
    private String student;
    private double grade;

    public StudentLesson(int id,
                         String title,
                         String description,
                         float maxGrade,
                         LocalDateTime creationDate,
                         String student,
                         double grade) {
        super(id, title, description, maxGrade, creationDate);
        this.student = student;
        this.grade = grade;
    }
}
