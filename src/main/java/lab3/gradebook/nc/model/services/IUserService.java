package lab3.gradebook.nc.model.services;

import lab3.gradebook.nc.model.RegistrationRequest;

public interface IUserService {
    void registerNewUserAccount(RegistrationRequest user) throws UserAlreadyExistException;
}
