package lab3.gradebook.nc.model.entities;

import lab3.gradebook.nc.model.StudyRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ScheduleEntry {
    private int subjectId;
    private String subjectName;
    private Lesson lesson;
    private StudyRole role;
}
