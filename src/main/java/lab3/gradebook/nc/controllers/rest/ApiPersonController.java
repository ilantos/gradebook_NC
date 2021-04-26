package lab3.gradebook.nc.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lab3.gradebook.nc.controllers.utils.CustomFormatResponseBody;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoPerson;
import lab3.gradebook.nc.model.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/users")
public class ApiPersonController {
    private DaoPerson daoPerson;
    private CustomFormatResponseBody customFormatResponseBody;

    @Autowired
    public ApiPersonController(DaoPerson daoPerson, CustomFormatResponseBody customFormatResponseBody) {
        this.daoPerson = daoPerson;
        this.customFormatResponseBody = customFormatResponseBody;
    }

    @GetMapping
    @ResponseBody
    public String allUsers() throws DAOException, JsonProcessingException {
        List<Person> personList = daoPerson.getPeoples();
        return customFormatResponseBody.buildResponse(true, personList);
    }

    @GetMapping("{id}")
    @ResponseBody
    public String getById(@PathVariable int id) throws JsonProcessingException, DAOException {
        Person person = daoPerson.getById(id);
        return person != null ?
                customFormatResponseBody.buildResponse(true, person) :
                customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find user by id");
    }

    @GetMapping("/login/{login}")
    @ResponseBody
    public String getByLogin(@PathVariable String login) throws JsonProcessingException, DAOException {
        Person person = daoPerson.getByLogin(login);
        return person != null ?
                customFormatResponseBody.buildResponse(true, person) :
                customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find user by login");
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public String delete(@PathVariable int id) throws JsonProcessingException {
        try {
            daoPerson.delete(id);
            return customFormatResponseBody.buildResponse(true, "User deleted successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(false, "User deleted unsuccessfully");
        }
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody Person person) throws JsonProcessingException {
        try {
            System.out.println(person.toString());
            daoPerson.edit(person);
            return ResponseEntity.ok(
                    customFormatResponseBody.buildResponse(
                            true,
                            "User edited successfully"));
        } catch (DAOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).body(
                    customFormatResponseBody.buildResponse(
                            false,
                            "User edited unsuccessfully"));
        }
    }

    @PostMapping
    @ResponseBody
    public String add(@RequestBody Person person) throws JsonProcessingException {
        try {
            daoPerson.add(person);
            return customFormatResponseBody.buildResponse(true, "User added successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(false, "User added unsuccessfully");
        }
    }
}
