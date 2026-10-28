package com.educandoweb.course.resources;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.Category;
import com.educandoweb.course.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService service;

    // Executa este método quando uma requisição HTTP GET
    // for feita para /categories.
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {

        // Solicita à camada Service todas as categorias cadastradas.
        List<Category> list = service.findAll();

        // Retorna HTTP 200 (OK), com a lista de categorias
        // no corpo da resposta HTTP.
        return ResponseEntity.ok().body(list);
    }

    // Executa este método quando uma requisição GET for feita
    // para /categories/{id}.
    // Exemplo: GET /categories/1 busca a categoria de ID 1.
    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {

        // Recebe o ID da URL e solicita ao Service a busca
        // da categoria correspondente.
        return ResponseEntity.of(service.findById(id));
    }
}
