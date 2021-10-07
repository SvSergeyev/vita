package tech.sergeyev.vitasoft.controller.deprecated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vitasoft.persistence.model.users.Person;

import java.util.List;

@RestController
@RequestMapping("/abc")
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {
    final PersonService personService;

    @Autowired
    public TestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/people")
    public ResponseEntity<List<Person>> read() {
        final List<Person> people = personService.getAll();
        return people != null && !people.isEmpty()
                ? new ResponseEntity<>(people, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<Person> read(@PathVariable(name = "id") int id) {
        final Person person = personService.getPersonById(id);
        return person != null
                ? new ResponseEntity<>(person, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id,
                                    @RequestBody Person person) {
        final boolean updated = personService.update(id, person);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("people/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id")int id) {
        final boolean deleted = personService.bDelete(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
