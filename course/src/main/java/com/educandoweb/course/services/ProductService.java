package com.educandoweb.course.services;

import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.Product;
import com.educandoweb.course.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) { //é um metodo usado para encontrar o ID
        Optional<Product> obj = repository.findById(id); // mostra que pode ser um metodo que pode não ter retorno
        return obj.get(); // quando é "200" ele retorna ao ID do objeto
    }
}
