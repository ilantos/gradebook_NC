package lab3.gradebook.nc.model.services;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationRequest {
    private int idLocation;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String password;
    private String email;
}
