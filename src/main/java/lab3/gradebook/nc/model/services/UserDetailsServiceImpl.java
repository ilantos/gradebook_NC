package lab3.gradebook.nc.model.services;

import lab3.gradebook.nc.model.Role;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoPerson;
import lab3.gradebook.nc.model.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String USER_NOT_FOUND_MSG = "User with login: %s not found";
    private DaoPerson daoPerson;

    @Autowired
    public UserDetailsServiceImpl(DaoPerson daoPerson) {
        this.daoPerson = daoPerson;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            Person person = daoPerson.getByLogin(login);
            if (person == null) throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, login));
            Role role = person.isAdmin() ? Role.ADMIN : Role.USER;
            return User.builder()
                    .username(person.getLogin())
                    .password(person.getPassword())
                    .roles(role.name())
                    .build();
        } catch (DAOException e) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, login), e);
        }
    }
}
