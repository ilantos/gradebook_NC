package lab3.gradebook.nc.model.services;

import lab3.gradebook.nc.model.RegistrationRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class ValidatorRegistrationRequest implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "firstName", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "lastName", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "patronymic", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "login", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "password", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "email", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "idLocation", "required");
    }
}
