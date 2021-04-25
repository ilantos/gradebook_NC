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
