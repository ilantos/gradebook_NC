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

    public SubjectWithGrades(int id,
                             String title,
                             String description,
                             List<StudentLesson> lessons) {
        super(id, title, description);
        this.lessons = lessons;
    }
}
