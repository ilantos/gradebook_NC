package lab3.gradebook.nc.model.entities;

import lombok.*;

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
