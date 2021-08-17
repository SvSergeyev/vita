package tech.sergeyev.vitasoft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vitasoft.persistence.dao.RequestRepository;
import tech.sergeyev.vitasoft.persistence.dao.RoleRepository;
import tech.sergeyev.vitasoft.persistence.model.requests.Statement;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;
import tech.sergeyev.vitasoft.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
@RequestMapping("/lk")
public class PersonalAccountController {
    private final PersonService personService;
    private final RoleRepository roleRepository;
    private final RequestRepository requestRepository;
    private final RequestService requestService;

    public PersonalAccountController(PersonService personService,
                                     RoleRepository roleRepository,
                                     RequestRepository requestRepository,
                                     RequestService requestService) {
        this.personService = personService;
        this.roleRepository = roleRepository;
        this.requestRepository = requestRepository;
        this.requestService = requestService;
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(PersonalAccountController.class);

    @GetMapping()
    public String identifyAndRedirect(HttpServletRequest request) {
        LOGGER.info("\n\n\nin identifyAndRedirect method\n\n");
        Person person = personService.getPersonByEmail(request.getUserPrincipal().getName());
        LOGGER.info("\n\n\nPERSON: " + person + "\n\n");
        if (request.isUserInRole("ADMIN")) {
            return "redirect:lk/admin/" + person.getId();
        } else if (request.isUserInRole("OPERATOR")) {
            return "redirect:lk/operator/" + person.getId();
        } else if (request.isUserInRole("USER")) {
            LOGGER.info("REDIRECT TO USER PAGE");
            return "redirect:lk/user/" + person.getId();
        }

        return "redirect:/login?logout";
    }

    @GetMapping({"/user/{id}", "/admin/{id}", "/operator/{id}"})
    public String show(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        model.addAttribute("person", personService.getPersonById(id));
        if (request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("users", personService.getAllPeopleByRole("ROLE_USER"));
            model.addAttribute("operators", personService.getAllPeopleByRole("ROLE_OPERATOR"));
            return "personal/admin";
        }
        else if (request.isUserInRole("ROLE_OPERATOR")) {
            return "personal/operator";
        }
        else if (request.isUserInRole("ROLE_USER")) {
            model.addAttribute("requests", requestRepository.findAllByAuthor((Person) model.getAttribute("person")));
            LOGGER.info("\n\n\nREQUESTS: " + model.getAttribute("requests") + "\n\n");
            return "personal/user";
        }
        else {
            return "redirect:/login?logout";
        }
    }

    @PatchMapping("**/admin/editUserRoles/{id}")
    public String changeRole(@PathVariable("id") int id, @ModelAttribute Person person) {
        person.setRoles(Collections.singleton(roleRepository.findByName("ROLE_OPERATOR")));
        personService.update(id, person);
        return "redirect:/lk";
    }
}
