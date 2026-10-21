package com.educandoweb.course.services;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //responsavel por repassar consultas simples
public class UserService  {

    @Autowired //injeção de depdendencia
    private UserRepository repository; // variavel que vai chamar o UserRepository

    public List<User> findAll() {
        return repository.findAll();
    }
    /*
    metodo que vai retornar a injeção de dependencia, quando for solicitado o serviço de
    listagem de todos os Users em formato de lista
     */

    public User findById(Long id) { //é um metodo usado para encontrar o ID
        Optional<User> obj = repository.findById(id); // mostra que pode ser um metodo que pode não ter retorno
        return obj.get(); // quando é "200" ele retorna ao ID do objeto

    }
}
