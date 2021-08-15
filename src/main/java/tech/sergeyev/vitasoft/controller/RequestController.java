package tech.sergeyev.vitasoft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
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

//    @GetMapping()
//    public String index(Model model, HttpServletRequest httpRequest) {
//        int id = personService.getPersonByEmail(
//                httpRequest.getUserPrincipal().getName())
//                .getId();
//        if (httpRequest.isUserInRole("ROLE_USER")) {
//            model.addAttribute("user_id", id);
//            return "redirect:/lk/user/{" + id + "}";
//        }
//        /*
//        Тут по-хорошему должна быть затычка, мол, раз не юзер - пнх
//         */
//        return null;
//    }

    @GetMapping({"/", "/new}"})
    public String index(Model model, HttpServletRequest httpRequest) {
        LOGGER.info("@GetMapping({\"/\", \"/new}\"})");
        Person user = personService.getPersonByEmail(httpRequest.getUserPrincipal().getName());
//        LOGGER.info("USER_ID (in GET_mapping): " + user_id);
        model.addAttribute("user", user);
//        LOGGER.info("MODEL_ATTRIBUTE (in GET_mapping): " + model.getAttribute("user_id"));
        if (!httpRequest.isUserInRole("ROLE_USER")) {
            return "redirect:/requests?access_denied";
        }
        model.addAttribute("request", new Request());
        return "request/new";
    }

    @PostMapping("/new")
    public String create(String message, HttpServletRequest httpRequest) {
        LOGGER.info("@PostMapping(\"/new\")");
        LOGGER.info("Message: " + message);
        Person author = personService.getPersonByEmail(httpRequest.getUserPrincipal().getName());
        LOGGER.info("USER: " + author);
        requestService.create(author, message);
        LOGGER.info("REDIRECT TO /lk/user/id");
        return ("redirect:/lk?r=success");
    }
}
