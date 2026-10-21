package com.educandoweb.course.config;

import com.educandoweb.course.OrderRepository.OrderRepository;
import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.entities.enums.OrderStatus;
import com.educandoweb.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.time.Instant;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")

public class TesteConfig implements CommandLineRunner {
    /*

    Classe implementada em uma interface (CommandLineRunner) que serve para fazer uma ação assim que a aplicação
    for inicializada. Que nesse caso será adicionar os dois usuarios abaixo.
    Então, nesse caso ele ta automatizando a inserção de alguns usuarios
     */

    @Autowired // injeta uma dependencia
    private UserRepository userRepository;

    @Autowired //injeta dependencia
    private OrderRepository orderRepository;

    /*
    TestConfig existe para testar a conexão entre ambos Repository, que vao chamar os dados, com o proprio
    banco de dados que armazena os dados
     */

    @Override //Overide aqui não seria nescessario para a aplicação rodar de maneira correta
    public void run(String... args) throws Exception {
        // tudo que colocar dentro desse metodo íra executar quando a aplicação for inicializada
        User u1 = new User("546389", "maria@gmail.com", "maria fulana", "123456");
        User u2 = new User("546378", "AlexGreen@gmail.com", "Alex Green", "123456");

        Order o1 = new Order(null, OrderStatus.PAID, u1, Instant.parse("2019-06-20T19:53:07Z"));
        Order o2 = new Order(null, OrderStatus.WAITING_PAYMENT, u2, Instant.parse("2019-07-21T03:42:10Z"));
        Order o3 = new Order(null, OrderStatus.WAITING_PAYMENT, u1, Instant.parse("2019-07-22T15:21:22Z"));
        userRepository.saveAll(Arrays.asList(u1, u2));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));
        /*
        solicita ao repositorio que os dois objetos sejam adicionados ao banco de dados em uma lista reservada
         */
    }
}
