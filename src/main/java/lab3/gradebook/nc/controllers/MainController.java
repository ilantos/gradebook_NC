package lab3.gradebook.nc.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(HttpServletResponse response) {
        response.addCookie(new Cookie("username", getCurrentUser()));
        response.addCookie(new Cookie("isAdmin", "true"));
        return "index";
    }

    @GetMapping("/homepage")
    public String homepage(HttpServletResponse response) {
        response.addCookie(new Cookie("username", getCurrentUser()));
        response.addCookie(new Cookie("isAdmin", "false"));
        return "forward:schedule";
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
    @GetMapping("/teaching")
    public String subjectsPageTeaching(HttpServletResponse response) {
        response.addCookie(new Cookie("personMode", "teacher"));
        return "pages/subjects_teaching";
    }

    @GetMapping("/studying")
    public String subjectsPageStudying(HttpServletResponse response) {
        response.addCookie(new Cookie("personMode", "student"));
        return "pages/subjects_studying";
    }
    @GetMapping("/schedule")
    public String userSchedulePage(HttpServletResponse response) {
        response.addCookie(new Cookie("username", getCurrentUser()));
        response.addCookie(new Cookie("personMode", "student"));
        return "pages/schedule";
    }

    @GetMapping("/subjects")
    public String subjectsPage() {
        return "pages/subjects";
    }

    @GetMapping("/subjects/{id}")
    public String subjectPage(@PathVariable int id,
                              HttpServletResponse response) {
        response.addCookie(new Cookie("idSubject", String.valueOf(id)));
        return "/pages/subject";
    }

    @GetMapping("/studying/subjects/{id}")
    public String subjectStudyingPage(@PathVariable int id,
                              HttpServletResponse response) {
        response.addCookie(new Cookie("idSubject", String.valueOf(id)));
        return "/pages/subject_studying";
    }

    @GetMapping("/subjects/add")
    public String addSubjectPage(HttpServletResponse response) {
        response.addCookie(new Cookie("formSubject", "add"));
        return "/pages/form_subject";
    }

    @GetMapping("/subjects/edit/{id}")
    public String editSubjectPage(@PathVariable int id,
                                  HttpServletResponse response) {
        response.addCookie(new Cookie("formSubject", "edit"));
        response.addCookie(new Cookie("idSubject", String.valueOf(id)));
        return "/pages/form_subject";
    }

    private String getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        throw new IllegalStateException("user is not authenticated");
    }
}
