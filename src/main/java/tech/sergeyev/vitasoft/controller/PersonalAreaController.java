package tech.sergeyev.vitasoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.dao.PersonRepository;

@Controller
@RequestMapping("/lk")
public class PersonalAreaController {
    private final PersonRepository personRepository;

    public PersonalAreaController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personRepository.findById(id));
        return ("personal/lk");
    }
}
