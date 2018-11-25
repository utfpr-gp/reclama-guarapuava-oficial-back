/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.reclamaguarapuava.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ronifabio
 */
@RestController
@RequestMapping("/hello")
@Api(value = "Hello Controller")
@CrossOrigin(origins = "*")
public class HelloController {

    @RequestMapping(method = GET)
    public String sayHello() {
        return "Hello World Spring";
    }

    @RequestMapping(path = {"/java"}, method = GET)
    public String sayHelloNovo() {
        return "Alô Mundo Java";
    }

}
