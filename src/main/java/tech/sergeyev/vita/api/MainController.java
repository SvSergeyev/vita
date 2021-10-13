package tech.sergeyev.vita.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/")
public class MainController {

    @GetMapping()
    public ResponseEntity<?> index() {
        return new ResponseEntity<>("Welcome to the main page, dude. It's demonstration web-service", HttpStatus.OK);
    }
}
