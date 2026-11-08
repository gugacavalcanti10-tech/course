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

    public Optional<User> findById(Long id) {
        return repository.findById(id);

    }
    public User insert(User obj) {
        return repository.save(obj);
    }
    /*
    tem como objetivo receber um objeto User, salva-lo nno bannco de dados e retornar o usuario salvo
     */

    public void delete(Long id) {
    repository.deleteById(id);
    }

    public User update(Long id, User obj){
        User entity = repository.getReferenceById(id);
        updateData(entity, obj);
        return repository.save(entity);
    }

    private void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setPhone(obj.getPhone());
        /*
        metodo de alteração de caracteristicas de um usuario, mas nem todsa as caracteristicas
        vão poder ser alteradas, principalmente o Id, porque é a chave primaria
         */
    }

    ;
}
