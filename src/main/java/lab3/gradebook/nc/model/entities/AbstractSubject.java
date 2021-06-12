package lab3.gradebook.nc.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSubject {
    private int id;
    private String title;
    private String description;
    private double targetGrade;
    private double sumMaxGrades;

    public AbstractSubject(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
