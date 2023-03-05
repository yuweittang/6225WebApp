package com.webapp.webapp.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HealthZController {
    @GetMapping("/healthz")
    public ResponseEntity<String> healthz() {

        return new ResponseEntity<>("", HttpStatus.OK);
    }

}