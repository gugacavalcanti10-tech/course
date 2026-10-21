package orderResource;

import com.educandoweb.course.OrderService.OrderService;
import com.educandoweb.course.entities.Order;
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
@RequestMapping(value = "/orders") //indica o caminho de requisição
public class OrderResource {

    @Autowired // diz ao Spring para injetar a dependencia da classe criada, na classe abaixo
    private OrderService service; //gerando atributo chamado de "service" cujo o tipo é OrderService

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        //Representa o metodo a ser executado quando uma requisição GET for realizada
        List<Order> list = service.findAll();
        return ResponseEntity.ok().body(list);
        //retorna um corpo de lista com a quantidade de usuários contidos

    }
    @GetMapping(value = "/{id}") // isso vai indicar que a requisição vai aceitar um ID
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order obj = service.findById(id); //o "findById" vai receber o parametro de ID, entregue ao GetMapping
        return  ResponseEntity.ok().body(obj); // para indicar que houve sucesso na resposta
        //o "(obj)" é o corpo da reposição

            /*
            ResponseEntity - representa uma completa a requisição

            PathVariable - indica que o atributo destinado como requisição HTPP pelo GetMapping tem que ser
            no tipo no qual foi indicado. Usado nesse caso para o Spring aceitar o valor "id" como parametro
            que vai chegar na URL
             */
    }
}
