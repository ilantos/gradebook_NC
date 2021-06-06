package lab3.gradebook.nc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lab3.gradebook.nc.controllers.utils.CustomFormatResponseBody;
import lab3.gradebook.nc.model.RegistrationRequest;
import lab3.gradebook.nc.model.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class
UserController {
    private IUserService userService;
    private SecurityService securityService;
    private CustomFormatResponseBody customFormatResponseBody;
    private ValidatorRegistrationRequest validator;

    @Autowired
    public UserController(IUserService userService,
                          SecurityService securityService,
                          CustomFormatResponseBody customFormatResponseBody,
                          ValidatorRegistrationRequest validator) {
        this.userService = userService;
        this.securityService = securityService;
        this.customFormatResponseBody = customFormatResponseBody;
        this.validator = validator;
    }

    @GetMapping("/login")
    public String login() {
        return "/pages/login";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "/pages/sign_up";
    }

    @GetMapping("/users")
    public String usersPage() {
        return "/pages/users";
    }

    @GetMapping("/users/{id}")
    public String userPage(@PathVariable String id, HttpServletResponse response) {
        response.addCookie(new Cookie("idUser", String.valueOf(id)));
        return "/pages/user";
    }
    @GetMapping("/subjectEnroll")
    public String subjectEnrollPage() {
        return "/pages/form_subject_enroll";
    }
    @GetMapping("/users/add")
    public String addSubjectPage(HttpServletResponse response) {
        response.addCookie(new Cookie("formUser", "add"));
        return "/pages/form_user";
    }

    @GetMapping("/users/edit/{id}")
    public String editSubjectPage(@PathVariable int id, HttpServletResponse response) {
        response.addCookie(new Cookie("formUser", "edit"));
        response.addCookie(new Cookie("idUser", String.valueOf(id)));
        return "/pages/form_user";
    }

    @PostMapping("/registration")
    @ResponseBody
    public ResponseEntity<?> registration(
            @RequestBody RegistrationRequest request,
            BindingResult bindingResult)
            throws JsonProcessingException {
        try {
            validator.validate(request, bindingResult);
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldErrors().get(0);
                throw new IllegalArgumentException(
                                        fieldError.getField() + " "
                                                + fieldError.getCode());
            }

            userService.registerNewUserAccount(request);
            securityService.autoLogin(
                    request.getLogin(),
                    request.getPassword());
            return ResponseEntity.ok(
                    customFormatResponseBody.buildResponse(
                            true,
                            "User registered successfully"));
        } catch (UserAlreadyExistException | IllegalArgumentException e) {
            return ResponseEntity.ok(
                    customFormatResponseBody.buildResponse(
                            false,
                            e.getMessage()));
        }
    }
}
