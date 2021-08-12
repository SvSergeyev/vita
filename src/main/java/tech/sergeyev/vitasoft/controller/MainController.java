package tech.sergeyev.vitasoft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;

@Controller
@RequestMapping("/")
public class MainController {
    public static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    PersonService personService;

    public MainController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model) {
        LOGGER.info("\n\n\nInside @GetMapping()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.getPersonByEmail(personService.loadUserByUsername(auth.getName()).getUsername());
        model.addAttribute("person", user);
        return ("redirect:/lk");
    }
}
