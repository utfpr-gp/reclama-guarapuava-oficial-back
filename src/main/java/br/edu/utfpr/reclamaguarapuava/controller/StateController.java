package br.edu.utfpr.reclamaguarapuava.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estados")
@Api(value = "State Controller")
@CrossOrigin(origins = "*")
public class StateController {

}
