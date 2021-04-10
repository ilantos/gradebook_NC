package lab3.gradebook.nc.model;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int id;
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isAdmin;
    private Location location;
    private List<Subject> subjectsToTeachList;
    private List<Subject> subjectsToLearnList;
}
