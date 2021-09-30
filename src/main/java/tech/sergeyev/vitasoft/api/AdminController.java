package tech.sergeyev.vitasoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;
import tech.sergeyev.vitasoft.service.PersonServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    // TODO: Работа над контроллером админа

    private final PersonServiceImpl personService;

    public AdminController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> show(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        if (!validateRole(username, "ROLE_ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Person> users = personService.getAllPeopleByRole("ROLE_USER");
        return users != null
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping("/asasa")
//    public ResponseEntity<List<Person>> showDaNeShow(HttpServletRequest request) {
//        Person admin = personService.getPersonByEmail(request.getUserPrincipal().getName());
//        if (!validateRole(admin.getEmail(), "ROLE_ADMIN")) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        List<Person> users = personService.getAllPeopleByRole("ROLE_USER");
//        return users != null
//                ? new ResponseEntity<>(users, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @PutMapping("/management/{userId}")
//    public ResponseEntity<?> editRole(@PathVariable(name = "userId") int userId) {
//        if (!validateRole(adminId)) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        System.out.println("\n\n\nREQUEST_BODY: " + person + "\n\n\n");
//        final boolean updated = personService.update(userId, person);
//        return updated
//                ? new ResponseEntity<>(HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
    /*
    --------------
    final boolean updated = personService.update(id, person);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    --------------
    @PatchMapping("/{id}/edit_roles/{u_id}")
    public String changeRole(@PathVariable("u_id") int u_id,
                             @PathVariable("id") int id) {
        Person modifyingUser = new Person();
        modifyingUser.setRoles(Collections.singleton(roleService.getRoleByName("ROLE_OPERATOR")));
        personServiceImpl.voidUpdate(u_id, modifyingUser);
        return "redirect:/";
    }
     */
//    @Override
//    public boolean validateRole(String username, String role) {
//        Person person = personService.getPersonById(id);
//        return person.getRoles().contains(new Role("ROLE_ADMIN"));
//    }
}
