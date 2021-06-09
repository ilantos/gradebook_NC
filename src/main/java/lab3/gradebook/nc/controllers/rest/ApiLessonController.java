package lab3.gradebook.nc.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lab3.gradebook.nc.controllers.utils.CustomFormatResponseBody;
import lab3.gradebook.nc.model.EditGradeRequest;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoLesson;
import lab3.gradebook.nc.model.entities.AddNewLessonRequest;
import lab3.gradebook.nc.model.entities.Lesson;
import lab3.gradebook.nc.model.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("api/lessons")
public class ApiLessonController {
    private DaoLesson daoLesson;
    private CustomFormatResponseBody customFormatResponseBody;

    @Autowired
    public ApiLessonController(
            DaoLesson daoLesson,
            CustomFormatResponseBody customFormatResponseBody) {
        this.daoLesson = daoLesson;
        this.customFormatResponseBody = customFormatResponseBody;
    }

    @GetMapping("{id}")
    @ResponseBody
    public String getLessonsByID(@PathVariable int id)
            throws JsonProcessingException, DAOException {
        Lesson lesson = daoLesson.getById(id);
        return lesson != null
                ? customFormatResponseBody.buildResponse(true, lesson)
                : customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find lesson by id");
    }

    @GetMapping("{id}/grades")
    @ResponseBody
    public String getStudentGrades(@PathVariable int id)
            throws DAOException, JsonProcessingException {
        Map<Person, Double> grades = daoLesson.getGradesOfStudents(id);
        if (grades.size() == 0) {
            return customFormatResponseBody.buildResponse(
                    false,
                    "Cannot find lesson by id");
        }
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode root = mapper.createArrayNode();
        for (Map.Entry<Person, Double> item: grades.entrySet()) {
            ObjectNode node = root.addObject();
            ObjectNode student = node.putObject("student");
            student.put("id", item.getKey().getId());
            student.put("firstName", item.getKey().getFirstName());
            student.put("lastName", item.getKey().getLastName());
            node.put("grade", item.getValue());
        }
        return customFormatResponseBody.buildResponse(true, root);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public String delete(@PathVariable int id)
            throws JsonProcessingException {
        try {
            daoLesson.delete(id);
            return customFormatResponseBody.buildResponse(
                    true, "Lesson deleted successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(
                    false, "Lesson deleted unsuccessfully");
        }
    }
    @PutMapping
    public ResponseEntity<?> edit(@RequestBody Lesson lesson)
            throws JsonProcessingException {
        try {
            daoLesson.edit(lesson);
            return ResponseEntity.ok(
                    customFormatResponseBody.buildResponse(
                            true,
                            "Lesson edited successfully"));
        } catch (DAOException e) {
            return ResponseEntity.status(400).body(
                    customFormatResponseBody.buildResponse(
                            false,
                            "Lesson edited unsuccessfully"));
        }
    }

    @PutMapping("/grades")
    public ResponseEntity<?> editGrade(@RequestBody EditGradeRequest request,
                                       Principal principal)
            throws DAOException, JsonProcessingException {
        //TODO сделать проверку на того,
        // может ли преподаватель изменять оценку у студента
        Lesson lesson = daoLesson.getById(request.getIdLesson());
        if (request.getGrade() > lesson.getMaxGrade()
                || request.getGrade() < 0
                || Double.isNaN(request.getGrade())) {
           return ResponseEntity.ok(customFormatResponseBody
                   .buildResponse(false,
                   "Grade isn't correct"));
        }
        daoLesson.editGrade(
                request.getIdLesson(),
                request.getIdPerson(),
                request.getGrade()
        );
        return ResponseEntity.status(400).body(
                customFormatResponseBody.buildResponse(
                true,
                "Lesson edited successfully"));
    }

    @PostMapping
    @ResponseBody
    public String add(@RequestBody AddNewLessonRequest requestBody,
                      @RequestParam String subjectId)
            throws JsonProcessingException {
        LocalDateTime startDate = LocalDateTime.parse(requestBody.getStartDate());
        System.out.println(startDate);
        Lesson lesson = new Lesson();
        lesson.setTitle(requestBody.getTitle());
        lesson.setDescription(requestBody.getDescription());
        lesson.setMaxGrade(requestBody.getMaxGrade());
        lesson.setStartDate(startDate);
        try {
            daoLesson.add(lesson, Integer.parseInt(subjectId));
            return customFormatResponseBody.buildResponse(
                    true, "Lesson added successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(
                    false, "Lesson added unsuccessfully");
        }
    }
}
