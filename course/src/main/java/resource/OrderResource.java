package resource;


import com.educandoweb.course.entities.Order;
import com.educandoweb.course.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService service;

    // Executa este método quando uma requisição HTTP GET
    // for feita para /orders.
    @GetMapping
    public ResponseEntity<List<Order>> findAll() {

        // Solicita à camada Service todos os pedidos cadastrados.
        List<Order> list = service.findAll();

        // Retorna HTTP 200 (OK), com a lista de pedidos no corpo
        // da resposta HTTP.
        return ResponseEntity.ok().body(list);
    }

    // Executa este método quando uma requisição GET for feita
    // para /orders/{id}.
    // Exemplo: GET /orders/1 busca o pedido de ID 1.
    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {

        // Recebe o ID informado na URL e solicita ao Service
        // a busca do pedido correspondente.
        Order obj = service.findById(id);

        // Retorna HTTP 200 (OK), contendo o pedido encontrado.
        return ResponseEntity.ok().body(obj);
    }
}

