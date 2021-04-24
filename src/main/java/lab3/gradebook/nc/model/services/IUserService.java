package lab3.gradebook.nc.model.services;

public interface IUserService {
    void registerNewUserAccount(RegistrationRequest user) throws UserAlreadyExistException;
}
