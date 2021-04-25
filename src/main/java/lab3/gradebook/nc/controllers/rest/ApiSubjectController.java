package lab3.gradebook.nc.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lab3.gradebook.nc.controllers.utils.CustomFormatResponseBody;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoSubject;
import lab3.gradebook.nc.model.entities.Subject;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
@RequestMapping("/api/subjects")
public class ApiSubjectController {
    private DaoSubject daoSubject;
    private CustomFormatResponseBody customFormatResponseBody;

    @Autowired
    public ApiSubjectController(DaoSubject daoSubject, CustomFormatResponseBody customFormatResponseBody) {
        this.daoSubject = daoSubject;
        this.customFormatResponseBody = customFormatResponseBody;
    }

    @GetMapping
    @ResponseBody
    public String getSubjects(@RequestParam(required = false) Integer lessonId) throws DAOException, JsonProcessingException {
        Objects response;
        if (lessonId == null) {
            return customFormatResponseBody.buildResponse(true, daoSubject.getAll());
        } else {
            return customFormatResponseBody.buildResponse(true, daoSubject.getByLessonId(lessonId));
        }
    }

    @GetMapping("{id}")
    @ResponseBody
    public String getSubjectById(@PathVariable int id) throws JsonProcessingException, DAOException {
        Subject subject = daoSubject.getById(id);
        return subject != null ?
                customFormatResponseBody.buildResponse(true, subject) :
                customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find location by id");
    }
    @DeleteMapping("{id}")
    @ResponseBody
    public String delete(@PathVariable int id) throws JsonProcessingException {
        try {
            daoSubject.delete(id);
            return customFormatResponseBody.buildResponse(true, "Subject deleted successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(false, "Subject deleted unsuccessfully");
        }
    }
    @PutMapping
    public ResponseEntity<?> edit(@RequestBody Subject subject) throws JsonProcessingException {
        try {
            daoSubject.edit(subject);
            return ResponseEntity.ok(
                    customFormatResponseBody.buildResponse(
                            true,
                            "Subject edited successfully"));
        } catch (DAOException e) {
            return ResponseEntity.status(400).body(
                    customFormatResponseBody.buildResponse(
                            false,
                            "Subject edited unsuccessfully"));
        }
    }

    @PostMapping
    @ResponseBody
    public String add(@RequestBody Subject subject) throws JsonProcessingException {
        try {
            daoSubject.add(subject);
            return customFormatResponseBody.buildResponse(true, "Subject added successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(false, "Subject added unsuccessfully");
        }
    }
}