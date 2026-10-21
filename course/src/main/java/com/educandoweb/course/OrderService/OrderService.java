package com.educandoweb.course.OrderService;

import com.educandoweb.course.OrderRepository.OrderRepository;
import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
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


    public Order findById(Long id) { //é um metodo usado para encontrar o ID
        Optional<Order> obj = repository.findById(id); // mostra que pode ser um metodo que pode não ter retorno
        return obj.get(); // quando é "200" ele retorna ao ID do objeto

    }
}
