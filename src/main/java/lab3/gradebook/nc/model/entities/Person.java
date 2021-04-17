package lab3.gradebook.nc.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    private int id;
    private int idLocation;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private boolean isAdmin;
}
