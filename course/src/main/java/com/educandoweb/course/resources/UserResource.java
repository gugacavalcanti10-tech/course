package com.educandoweb.course.resources;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
            User obj = service.findById(id);
            return ResponseEntity.ok().body(obj);
            /*
            ResponseEntity - representa uma completa a requisição

            PathVariable - indica que o atributo destinado como requisição HTPP pelo GetMapping tem que ser
            no tipo no qual foi indicado. Usado nesse caso para o Spring aceitar o valor "id" como parametro
            que vai chegar na URL
             */

        /*
        esses dois GetMapping's tem como função ser um endpoint, ou seja, responnder a uma requisição http,
        um deles sendo a requisição por usuario no geral, outro especificando o ID do usuario
         */
        }

        @PostMapping
        public ResponseEntity<User> Insert(@RequestBody User obj) {
            obj = service.insert(obj);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(obj.getId()).toUri();

            return ResponseEntity.created(uri).body(obj);
         }

        @DeleteMapping(value = "/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id){
            service.delete(id);
            return ResponseEntity.noContent().build();
        }

        @PutMapping(value = "/{id}")
        public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
            obj = service.update(id, obj);
            return ResponseEntity.ok().body(obj);
        }
    }
