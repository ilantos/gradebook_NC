package lab3.gradebook.nc.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lab3.gradebook.nc.controllers.utils.CustomFormatResponseBody;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoSubject;
import lab3.gradebook.nc.model.entities.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/students")
public class ApiStudentController {
    private CustomFormatResponseBody customFormatResponseBody;
    private DaoSubject daoSubject;
    public ApiStudentController(CustomFormatResponseBody customFormatResponseBody, DaoSubject daoSubject) {
        this.customFormatResponseBody = customFormatResponseBody;
        this.daoSubject = daoSubject;
    }

    @GetMapping
    public ResponseEntity<String> getStudentsBySubject(@RequestParam int subject_id) throws JsonProcessingException {
        try {
            List<Person> personList = daoSubject.getStudentsBySubjectId(subject_id);
            return ResponseEntity.status(HttpStatus.OK).body(
                            customFormatResponseBody.buildResponse(true, personList)
                    );
        } catch (DAOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).body(
                    customFormatResponseBody.buildResponse(
                            false,
                            "Cannot find students of this subject"));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentFromSubject(@PathVariable int id, @RequestParam int subject_id) throws JsonProcessingException {
        try {
            daoSubject.deleteStudentFromSubject(id, subject_id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    customFormatResponseBody.buildResponse(true, "student is deleted")
            );
        } catch (DAOException | JsonProcessingException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(400).body(
                    customFormatResponseBody.buildResponse(
                            false,
                            "Cannot delete student from this subject"));
        }
    }
}
