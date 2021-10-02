package tech.sergeyev.vitasoft.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.sergeyev.vitasoft.service.PersonServiceImpl;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping()
    public String index() {
        return "Main page";
    }
}
