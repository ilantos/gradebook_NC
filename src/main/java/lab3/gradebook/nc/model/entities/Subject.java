package lab3.gradebook.nc.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class Subject extends AbstractSubject {
    private List<Lesson> lessons;

    public Subject(int id,
                   String title,
                   String description,
                   List<Lesson> lessons) {
        super(id, title, description);
        this.lessons = lessons;
    }
}
