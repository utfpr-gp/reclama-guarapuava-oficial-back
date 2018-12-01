/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.reclamaguarapuava.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.reclamaguarapuava.model.Occurrence;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Getter
public class Response<T extends EntityApplication> {
    private final T data;
    private final URI entityDetailsUri;

    public Response(T data) {
        this.data = data;
        this.entityDetailsUri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(data.getId()).toUri();
    }
}
