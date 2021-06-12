package lab3.gradebook.nc.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SubjectWithGrades extends AbstractSubject {
    private List<StudentLesson> lessons;
    private double sumStudentGrades;

    public SubjectWithGrades(int id,
                             String title,
                             String description,
                             List<StudentLesson> lessons) {
        super(id, title, description);
        this.lessons = lessons;

        if (lessons != null && lessons.size() != 0) {
            double sumMaxGrades = 0;
            double sumStudentGrades = 0;
            for (StudentLesson lesson: lessons) {
                sumMaxGrades += lesson.getMaxGrade();
                sumStudentGrades += lesson.getGrade();
            }
            setSumMaxGrades(sumMaxGrades);
            setTargetGrade(sumMaxGrades * 0.60);
            setSumStudentGrades(sumStudentGrades);
        }
    }
}
