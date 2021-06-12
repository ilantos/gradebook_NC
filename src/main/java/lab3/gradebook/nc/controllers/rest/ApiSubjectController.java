package lab3.gradebook.nc.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lab3.gradebook.nc.controllers.utils.CustomFormatResponseBody;
import lab3.gradebook.nc.model.StudyRole;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoPerson;
import lab3.gradebook.nc.model.db.DaoSubject;
import lab3.gradebook.nc.model.entities.Person;
import lab3.gradebook.nc.model.entities.SubjectWithGrades;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/subjects")
public class ApiSubjectController {
    private DaoSubject daoSubject;
    private DaoPerson daoPerson;
    private CustomFormatResponseBody customFormatResponseBody;

    @Autowired
    public ApiSubjectController(DaoSubject daoSubject,
                                DaoPerson daoPerson,
                                CustomFormatResponseBody
                                            customFormatResponseBody) {
        this.daoSubject = daoSubject;
        this.daoPerson = daoPerson;
        this.customFormatResponseBody = customFormatResponseBody;
    }

    @GetMapping
    @ResponseBody
    public String getSubjects() throws DAOException, JsonProcessingException {
        List<Subject> subjectList = daoSubject.getAll();
        return customFormatResponseBody.buildResponse(true, subjectList);
    }

    @GetMapping("teaching/{login}")
    @ResponseBody
    public String teachingSubjects(@PathVariable String login)
            throws DAOException, JsonProcessingException {
        List<Subject> subjects = daoSubject
                .getByLoginAndRole(login, StudyRole.TEACHER);
        return customFormatResponseBody.buildResponse(true, subjects);
    }

    @GetMapping("studying/{login}")
    @ResponseBody
    public String studyingSubjects(@PathVariable String login)
            throws DAOException, JsonProcessingException {
        List<Subject> subjects = daoSubject
                .getByLoginAndRole(login, StudyRole.STUDENT);
        return customFormatResponseBody.buildResponse(true, subjects);
    }

    @GetMapping("{id}")
    @ResponseBody
    public String getSubjectById(@PathVariable int id)
            throws JsonProcessingException, DAOException {
        Subject subject = daoSubject.getById(id);
        return subject != null
                ? customFormatResponseBody.buildResponse(true, subject)
                : customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find location by id");
    }

    @GetMapping("{id}/teacher")
    @ResponseBody
    public String getTeacherOfSubject(@PathVariable int id)
            throws DAOException, JsonProcessingException {
        Person teacher = daoPerson.getTeacherOfSubject(id);
        return teacher != null
                ? customFormatResponseBody.buildResponse(true,
                teacher.getFirstName() + " "
                        + teacher.getLastName() + " "
                        + teacher.getPatronymic())
                : customFormatResponseBody.buildResponse(
                false,
                "Cannot find teacher");
    }

    @GetMapping("/{id}/studying")
    @ResponseBody
    public String getSubjectWithGradesById(@PathVariable int id,
                                           Principal principal)
            throws JsonProcessingException, DAOException {
        SubjectWithGrades subject = daoSubject
                .getWithGradesById(id, principal.getName());
        return subject != null
                ? customFormatResponseBody.buildResponse(true, subject)
                : customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find location by id");
    }
    @PostMapping("/{username}/enroll")
    @ResponseBody
    public String enrollUserInSubject(@PathVariable String username,
                                      @RequestParam int subjectId)
            throws JsonProcessingException {
        try {
            daoSubject.enrollUserInSubject(username, subjectId);
            return customFormatResponseBody
                    .buildResponse(true, "User enrolled in subject");
        } catch (DAOException e) {
            return customFormatResponseBody
                    .buildResponse(false,
                            "Can't enroll user \""
                                    + username
                                    + "\" in subject ");
        }
    }
    @GetMapping("/{username}/unrolledSubjects")
    @ResponseBody
    public String getUnrolledSubjects(@PathVariable String username)
            throws JsonProcessingException {
        try {
            return customFormatResponseBody
                    .buildResponse(true,
                            daoSubject
                                    .getUnrolledSubjectsByUsername(username));
        } catch (DAOException e) {
            return customFormatResponseBody
                    .buildResponse(false,
                            "Can't get unrolled subjects: "
                                    + e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ResponseBody
    public String delete(@PathVariable int id) throws JsonProcessingException {
        try {
            daoSubject.delete(id);
            return customFormatResponseBody
                    .buildResponse(true, "Subject deleted successfully");
        } catch (DAOException e) {
            return customFormatResponseBody
                    .buildResponse(false, "Subject deleted unsuccessfully");
        }
    }
    @PutMapping
    public ResponseEntity<?> edit(@RequestBody Subject subject)
            throws JsonProcessingException {
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
    public String add(@RequestBody Subject subject)
            throws JsonProcessingException {
        try {
            daoSubject.add(subject);
            return customFormatResponseBody
                    .buildResponse(true, "Subject added successfully");
        } catch (DAOException e) {
            return customFormatResponseBody
                    .buildResponse(false, "Subject added unsuccessfully");
        }
    }
}
