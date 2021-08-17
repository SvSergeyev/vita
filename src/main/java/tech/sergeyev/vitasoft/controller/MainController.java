package tech.sergeyev.vitasoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class MainController {
    private final PersonService personService;

    public MainController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String index(HttpServletRequest request) {
        Person person = personService.getPersonByEmail(request.getUserPrincipal().getName());
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin/" + person.getId();
        } else if (request.isUserInRole("OPERATOR")) {
            return "redirect:/operator/" + person.getId();
        } else if (request.isUserInRole("USER")) {
            return "redirect:/user/" + person.getId();
        }
        return "redirect:/login?logout";
    }
}
