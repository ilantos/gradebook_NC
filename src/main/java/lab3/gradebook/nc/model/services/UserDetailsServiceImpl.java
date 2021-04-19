package lab3.gradebook.nc.model.services;

import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoPerson;
import lab3.gradebook.nc.model.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
            return new User(person.getLogin(), person.getPassword(), new ArrayList<>());
        } catch (DAOException e) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, login), e);
        }
    }
}
