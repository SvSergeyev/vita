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
@RequestMapping("/requests")
public class RequestController {
    private final RequestService requestService;
    private final PersonService personService;
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    public RequestController(RequestService requestService,
                             PersonService personService) {
        this.requestService = requestService;
        this.personService = personService;
    }

    @GetMapping({"", "/new}"})
    public String index(Model model, HttpServletRequest httpRequest) {
        Person user = personService.getPersonByEmail(httpRequest.getUserPrincipal().getName());
        model.addAttribute("user", user);
        if (httpRequest.isUserInRole("ROLE_USER")) {
            model.addAttribute("request", new Request());
            return "request/create";
        }
        return "redirect:/";
    }

    @PostMapping("/new")
    public String create(String message, HttpServletRequest httpRequest) {
        Person author = personService.getPersonByEmail(httpRequest.getUserPrincipal().getName());
        requestService.create(author, message);
        return ("redirect:/user/" + author.getId() + "?r=success");
    }

    @GetMapping("/{r_id}/edit")
    public String view(@PathVariable("r_id") int id, Model model) {
        Request userRequest = requestService.getRequestById(id);
        model.addAttribute("request", userRequest);
        return "request/edit";
    }

    @PatchMapping("/{r_id}/edit")
    public String view(@PathVariable("r_id") int id, String message) {
        requestService.updateText(id, message);
        return "redirect:/";
    }

    @PatchMapping("/{id}/submit")
    public String send(@PathVariable("id") int id) {
        requestService.updateStatus(id, Statement.SUBMITTED);
        return "redirect:/";
    }

    @PatchMapping("/{id}/accept")
    public String accept(@PathVariable("id") int id) {
        requestService.updateStatus(id, Statement.ACCEPTED);
        return "redirect:/";
    }

    @PatchMapping("/{id}/reject")
    public String reject(@PathVariable("id") int id) {
        requestService.updateStatus(id, Statement.REJECTED);
        return "redirect:/";
    }
}
