package tech.sergeyev.vitasoft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.dao.PersonRepository;
import tech.sergeyev.vitasoft.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/lk")
public class PersonalAreaController {
    private final PersonService personService;

    public PersonalAreaController(PersonService personService) {
        this.personService = personService;
    }
    private final static Logger LOGGER = LoggerFactory.getLogger(PersonalAreaController.class);

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.getPersonById(id));
        LOGGER.info("Получил в модели: " + Objects.requireNonNull(model.getAttribute("person")).toString());
        return ("personal/lk");
    }
}
