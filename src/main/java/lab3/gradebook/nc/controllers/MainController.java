package lab3.gradebook.nc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/locations")
    public String locationPage() {
        return "pages/locations";
    }

    @GetMapping("/addLocation")
    public String addLocationPage() {
        return "pages/form_add_location";
    }
}
