package lab3.gradebook.nc.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class Subject {
    private int id;
    private String title;
    private String description;
    private List<Lesson> lessons;
}
