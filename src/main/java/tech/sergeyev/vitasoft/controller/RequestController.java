package tech.sergeyev.vitasoft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
import tech.sergeyev.vitasoft.persistence.model.requests.Statement;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;
import tech.sergeyev.vitasoft.service.RequestService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("**/requests")
public class RequestController {
    private final RequestService requestService;
    private final PersonService personService;
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    public RequestController(RequestService requestService,
                             PersonService personService) {
        this.requestService = requestService;
        this.personService = personService;
    }

    @GetMapping({"/", "/new}"})
    public String index(Model model, HttpServletRequest httpRequest) {
        Person user = personService.getPersonByEmail(httpRequest.getUserPrincipal().getName());
        model.addAttribute("user", user);
        if (!httpRequest.isUserInRole("ROLE_USER")) {
            return "redirect:/requests?access_denied";
        }
        model.addAttribute("request", new Request());
        return "request/create";
    }

    @PostMapping("/new")
    public String create(String message, HttpServletRequest httpRequest) {
        Person author = personService.getPersonByEmail(httpRequest.getUserPrincipal().getName());
        requestService.create(author, message);
        return ("redirect:/lk/user/" + author.getId() + "?r=success");
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable int id, Model model) {
        Request userRequest = requestService.getById(id);
        model.addAttribute("request", userRequest);
        return "request/edit";
    }

    @PatchMapping("/{id}")
    public String send(@PathVariable("id") int id) {
        requestService.updateStatus(id, Statement.SUBMITTED);
        return "redirect:/";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, String message) {
        requestService.updateText(id, message);
        return "redirect:/";
    }
}
