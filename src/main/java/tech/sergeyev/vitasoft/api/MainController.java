package tech.sergeyev.vitasoft.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.model.users.Person;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class MainController {
    private final PersonService personServiceImpl;

    public MainController(PersonService personServiceImpl) {
        this.personServiceImpl = personServiceImpl;
    }

    @GetMapping()
    public String index(HttpServletRequest request) {
        Person person = personServiceImpl.getPersonByEmail(request.getUserPrincipal().getName());
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admins";
        } else if (request.isUserInRole("OPERATOR")) {
            return "redirect:/operators/" + person.getId();
        } else if (request.isUserInRole("USER")) {
            return "redirect:/users/" + person.getId();
        }
        return "redirect:/login?logout";
    }
}
