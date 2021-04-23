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
        return "index";
    }

    @GetMapping("/locations")
    public String locationsPage() {
        return "pages/locations";
    }

    @GetMapping("/location/{id}")
    public String locationPage(@PathVariable int id,
                               HttpServletResponse response) {
        response.addCookie(new Cookie("idLocation", String.valueOf(id)));
        return "/pages/location";
    }

    @GetMapping("/location/add")
    public String addLocationPage(HttpServletResponse response) {
        response.addCookie(new Cookie("formLocation", "add"));
        return "/pages/form_location";
    }

    @GetMapping("/location/edit/{id}")
    public String addLocationPage(@PathVariable int id,
                                  HttpServletResponse response) {
        response.addCookie(new Cookie("formLocation", "edit"));
        response.addCookie(new Cookie("idLocation", String.valueOf(id)));
        return "/pages/form_location";
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
