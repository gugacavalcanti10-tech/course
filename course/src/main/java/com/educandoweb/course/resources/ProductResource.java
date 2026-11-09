package com.educandoweb.course.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.course.entities.Product;
import com.educandoweb.course.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService service;

    // Executa este método quando uma requisição HTTP GET
    // for feita para /products.
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {

        // Solicita à camada Service a lista de todos os produtos.
        List<Product> list = service.findAll();

        // Retorna HTTP 200 (OK), com a lista de produtos
        // no corpo da resposta HTTP.
        return ResponseEntity.ok().body(list);
    }

    // Executa este método quando uma requisição GET for feita
    // para /products/{id}.
    // Exemplo: GET /products/1 busca o produto de ID 1.
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {

        // Recebe o ID da URL e solicita ao Service a busca
        // do produto correspondente.
        return ResponseEntity.ok().body(service.findById(id));
    }
}
