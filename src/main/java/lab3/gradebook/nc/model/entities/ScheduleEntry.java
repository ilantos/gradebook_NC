package lab3.gradebook.nc.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleEntry {
    private String subjectName;
    private Lesson lesson;
}
