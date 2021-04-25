package lab3.gradebook.nc.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lab3.gradebook.nc.controllers.utils.CustomFormatResponseBody;
import lab3.gradebook.nc.model.db.DAOException;
import lab3.gradebook.nc.model.db.DaoLesson;
import lab3.gradebook.nc.model.entities.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/lessons")
public class ApiLessonController {
    private DaoLesson daoLesson;
    private CustomFormatResponseBody customFormatResponseBody;

    @Autowired
    public ApiLessonController(DaoLesson daoLesson, CustomFormatResponseBody customFormatResponseBody) {
        this.daoLesson = daoLesson;
        this.customFormatResponseBody = customFormatResponseBody;
    }

    @GetMapping("{id}")
    @ResponseBody
    public String getLessonsByID(@PathVariable int id) throws JsonProcessingException, DAOException {
        Lesson lesson = daoLesson.getById(id);
        return lesson != null ?
                customFormatResponseBody.buildResponse(true, lesson) :
                customFormatResponseBody.buildResponse(
                        false,
                        "Cannot find lesson by id");
    }
    @DeleteMapping("{id}")
    @ResponseBody
    public String delete(@PathVariable int id) throws JsonProcessingException {
        try {
            daoLesson.delete(id);
            return customFormatResponseBody.buildResponse(true, "Lesson deleted successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(false, "Lesson deleted unsuccessfully");
        }
    }
    @PutMapping
    public ResponseEntity<?> edit(@RequestBody Lesson lesson) throws JsonProcessingException {
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

    @PostMapping
    @ResponseBody
    public String add(@RequestBody Lesson lesson, @RequestParam String subjectId) throws JsonProcessingException {
        try {
            daoLesson.add(lesson, Integer.parseInt(subjectId));
            return customFormatResponseBody.buildResponse(true, "Lesson added successfully");
        } catch (DAOException e) {
            return customFormatResponseBody.buildResponse(false, "Lesson added unsuccessfully");
        }
    }
}
