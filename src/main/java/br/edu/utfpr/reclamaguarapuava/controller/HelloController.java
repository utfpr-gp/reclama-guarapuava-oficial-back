/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.reclamaguarapuava.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author ronifabio
 */
@RestController
@RequestMapping("/hello")
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
