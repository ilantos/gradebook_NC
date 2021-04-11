package lab3.gradebook.nc.controllers;

import lab3.gradebook.nc.model.entities.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @GetMapping
    public ResponseEntity<List<Subject>> getSubjects() {
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Subject>> getSubjects(@PathVariable String id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
