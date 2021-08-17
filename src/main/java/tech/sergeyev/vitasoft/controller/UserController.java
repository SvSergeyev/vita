package tech.sergeyev.vitasoft.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;
import tech.sergeyev.vitasoft.service.RequestService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RequestService requestService;
    private final PersonService personService;

    public UserController(RequestService requestService,
                              PersonService personService) {
        this.requestService = requestService;
        this.personService = personService;
    }

    @GetMapping()
    public String redirectToPersonalPage(HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_USER")) {
            return "redirect:/";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person requestingUser = personService.getPersonByEmail(auth.getName());
        return "redirect:/user/" + requestingUser.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, HttpServletRequest httpRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person requestingUser = personService.getPersonByEmail(auth.getName());
        if (id != requestingUser.getId() || !httpRequest.isUserInRole("ROLE_USER")) {
            return "redirect:/";
        }
        model.addAttribute("person", requestingUser);
        model.addAttribute("requests", requestService.getAllByAuthor(personService.getPersonById(id)));
        return "personal/user";
    }
}
