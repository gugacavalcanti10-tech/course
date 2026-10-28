package com.educandoweb.course.resources;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController //representa que vai receber requisições http
    @RequestMapping(value = "/users") //indica o caminho de requisição
    public class UserResource {
    
        @Autowired // diz ao Spring para injetar a dependencia da classe criada, na classe abaixo
        private UserService service; //gerando atributo chamado de "service" cujo o tipo é UserService
    
        @GetMapping
        public ResponseEntity<List<User>> findAll() {
          //Representa o metodo a ser executado quando uma requisição GET for realizada
            List<User> list = service.findAll();
            return ResponseEntity.ok().body(list);
            //retorna um corpo de lista com a quantidade de usuários contidos

    }
    @GetMapping(value = "/{id}") // isso vai indicar que a requisição vai aceitar um ID
     public ResponseEntity<User> findById(@PathVariable Long id) {
            return ResponseEntity.of(service.findById(id));

            /*
            ResponseEntity - representa uma completa a requisição

            PathVariable - indica que o atributo destinado como requisição HTPP pelo GetMapping tem que ser
            no tipo no qual foi indicado. Usado nesse caso para o Spring aceitar o valor "id" como parametro
            que vai chegar na URL
             */
    }
}
