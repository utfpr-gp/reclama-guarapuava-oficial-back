package br.edu.utfpr.reclamaguarapuava.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comentarios")
@Api(value = "Comment Controller")
@CrossOrigin(origins = "*")
public class CommentController {

}
