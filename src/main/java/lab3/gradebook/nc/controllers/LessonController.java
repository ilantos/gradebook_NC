package lab3.gradebook.nc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LessonController {
    @GetMapping("/lessons/{id}")
    public String lessonPage(@PathVariable int id,
                             HttpServletResponse response) {
        response.addCookie(new Cookie("idLesson", String.valueOf(id)));
        return "/pages/lesson";
    }

    @GetMapping("lessons/{id}/grades")
    public String gradesPage(@PathVariable int id,
                             HttpServletResponse response) {
        response.addCookie(new Cookie("idLesson", String.valueOf(id)));
        return "/pages/lesson_grades";
    }

    @GetMapping("/lessons/add")
    public String addLessonPage(HttpServletResponse response,
                                @RequestParam String subjectId) {
        response.addCookie(new Cookie("idSubject", subjectId));
        response.addCookie(new Cookie("formLesson", "add"));
        return "/pages/form_lesson";
    }

    @GetMapping("/lessons/edit/{id}")
    public String editLessonPage(@PathVariable int id,
                                 HttpServletResponse response) {
        response.addCookie(new Cookie("formLesson", "edit"));
        response.addCookie(new Cookie("idLesson", String.valueOf(id)));
        return "/pages/form_lesson";
    }
}
