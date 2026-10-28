package com.educandoweb.course.services;

import com.educandoweb.course.repositories.OrderRepository;
import com.educandoweb.course.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired //injeção de depdendencia
    private OrderRepository repository; // variavel que vai chamar o UserRepository

    public List<Order> findAll() {
        return repository.findAll();
    }


    public Optional<Order> findById(Long id) {
        return repository.findById(id);

    }
}
