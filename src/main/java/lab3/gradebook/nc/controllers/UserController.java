package lab3.gradebook.nc.controllers;

import lab3.gradebook.nc.model.services.IUserService;
import lab3.gradebook.nc.model.services.RegistrationRequest;
import lab3.gradebook.nc.model.services.SecurityService;
import lab3.gradebook.nc.model.services.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private IUserService userService;
    private SecurityService securityService;

    @Autowired
    public UserController(IUserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/login")
    public String login() {
        return "/pages/login";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "/pages/sign_up";
    }

    @PostMapping("/registration")
    public String registration(@RequestBody RegistrationRequest request) {
        try {
            userService.registerNewUserAccount(request);
            securityService.autoLogin(request.getLogin(), request.getPassword());
            return "redirect:/";
        } catch (UserAlreadyExistException e) {
            return "redirect:sign-up";
        }
    }
}
