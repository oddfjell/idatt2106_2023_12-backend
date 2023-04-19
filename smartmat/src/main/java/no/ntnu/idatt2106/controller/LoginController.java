package no.ntnu.idatt2106.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/login")
@RestController
public class LoginController {

    @PostMapping("/")
    public ResponseEntity<?> login(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

}
