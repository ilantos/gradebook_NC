package lab3.gradebook.nc.model.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
