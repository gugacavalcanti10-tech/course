package com.educandoweb.course.repositories;

import com.educandoweb.course.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    /*
    O UserRepository é uma interface utilizada para disponibilizar operações de acesso e manipulação dos dados
    dos usuários no banco de dados. Ela herda os métodos da interface JpaRepository,
    pertencente ao Spring Data JPA, que fornece operações prontas, como buscar, cadastrar,
    atualizar e excluir registros, sem a necessidade de implementar manualmente cada método básico.

    User indica a entidade que vai sofrer as operações de metodo e Long é o tipo da entidade
     */
}

