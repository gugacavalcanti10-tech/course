package com.educandoweb.course.services;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DataBaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

   public User findById(Long id) {
       Optional<User> obj = repository.findById(id);
       return obj.orElseThrow(() -> new ResourceNotFoundException(id));
   }
    public User insert(User obj) {
        return repository.save(obj);
    }
    /*
    tem como objetivo receber um objeto User, salva-lo nno bannco de dados e retornar o usuario salvo
     */

    @Transactional
    public void delete(Long id) {
        User entity = findById(id);
        try {
            repository.delete(entity);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Não é possível excluir o usuário porque existem registros vinculados a ele.");
        }
    }

    @Transactional
    public User update(Long id, User obj){
        User entity = findById(id);
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


}
