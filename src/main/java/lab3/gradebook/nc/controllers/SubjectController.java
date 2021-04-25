package lab3.gradebook.nc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SubjectController {
    @GetMapping("/subjects")
    public String subjectsPage() {
        return "pages/subjects";
    }

    @GetMapping("/subjects/{id}")
    public String subjectPage(@PathVariable int id, HttpServletResponse response) {
        response.addCookie(new Cookie("idSubject", String.valueOf(id)));
        return "/pages/subject";
    }

    @GetMapping("/subjects/add")
    public String addSubjectPage(HttpServletResponse response) {
        response.addCookie(new Cookie("formSubject", "add"));
        return "/pages/form_subject";
    }

    @GetMapping("/subjects/edit/{id}")
    public String editSubjectPage(@PathVariable int id, HttpServletResponse response) {
        response.addCookie(new Cookie("formSubject", "edit"));
        response.addCookie(new Cookie("idSubject", String.valueOf(id)));
        return "/pages/form_subject";
    }
}
