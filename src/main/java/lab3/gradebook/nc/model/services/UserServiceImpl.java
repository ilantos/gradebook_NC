package lab3.gradebook.nc.model.services;

import lab3.gradebook.nc.model.RegistrationRequest;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoPerson;
import lab3.gradebook.nc.model.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    private static final String USER_ALREADY_EXISTS_MSG = "There is an account with the login/email already exist";
    private DaoPerson daoPerson;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(DaoPerson daoPerson, PasswordEncoder passwordEncoder) {
        this.daoPerson = daoPerson;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerNewUserAccount(RegistrationRequest user) throws UserAlreadyExistException {
        Person person = new Person(
                1,
                user.getIdLocation(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                user.getLogin(),
                passwordEncoder.encode(user.getPassword()),
                user.getEmail(),
                false
        );

        try {
            daoPerson.add(person);
        } catch (DAOException e) {
            throw new UserAlreadyExistException(USER_ALREADY_EXISTS_MSG, e);
        }
    }
}
