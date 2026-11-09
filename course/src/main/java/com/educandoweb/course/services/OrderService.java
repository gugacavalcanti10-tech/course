package com.educandoweb.course.services;

import com.educandoweb.course.repositories.OrderRepository;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
import com.educandoweb.course.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    @Autowired //injeção de depdendencia
    private OrderRepository repository; // variavel que vai chamar o UserRepository

    public List<Order> findAll() {
        return repository.findAll();
    }


    public Order findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

    }
}
