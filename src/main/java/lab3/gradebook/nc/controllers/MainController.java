package lab3.gradebook.nc.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(HttpServletResponse response) {
        response.addCookie(new Cookie("username", getCurrentUser()));
        return "index";
    }

    @GetMapping("/locations")
    public String locationsPage() {
        return "pages/locations";
    }

    @GetMapping("/locations/{id}")
    public String locationPage(@PathVariable int id,
                               HttpServletResponse response) {
        response.addCookie(new Cookie("idLocation", String.valueOf(id)));
        return "/pages/location";
    }

    @GetMapping("/locations/add")
    public String addLocationPage(HttpServletResponse response) {
        response.addCookie(new Cookie("formLocation", "add"));
        return "/pages/form_location";
    }

    @GetMapping("/locations/edit/{id}")
    public String editLocationPage(@PathVariable int id,
                                  HttpServletResponse response) {
        response.addCookie(new Cookie("formLocation", "edit"));
        response.addCookie(new Cookie("idLocation", String.valueOf(id)));
        return "/pages/form_location";
    }
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
    @GetMapping("/lessons/{id}")
    public String lessonPage(@PathVariable int id, HttpServletResponse response) {
        response.addCookie(new Cookie("idLesson", String.valueOf(id)));
        return "/pages/lesson";
    }

    @GetMapping("/lessons/add")
    public String addLessonPage(HttpServletResponse response, @RequestParam String subjectId) {
        response.addCookie(new Cookie("idSubject", subjectId));
        response.addCookie(new Cookie("formLesson", "add"));
        return "/pages/form_lesson";
    }

    @GetMapping("/lessons/edit/{id}")
    public String editLessonPage(@PathVariable int id, HttpServletResponse response) {
        response.addCookie(new Cookie("formLesson", "edit"));
        response.addCookie(new Cookie("idLesson", String.valueOf(id)));
        return "/pages/form_lesson";
    }

    private String getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return "not authenticated user";
    }
}
