package com.educandoweb.course.config;

import com.educandoweb.course.repositories.OrderRepository;
import com.educandoweb.course.entities.Category;
import com.educandoweb.course.entities.Order;
import com.educandoweb.course.entities.Product;
import com.educandoweb.course.entities.User;
import com.educandoweb.course.entities.enums.OrderStatus;
import com.educandoweb.course.repositories.CategoryRepository;
import com.educandoweb.course.repositories.ProductRepository;
import com.educandoweb.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

    @Autowired //injeta dependencia
    private CategoryRepository categoryRepository;

    @Autowired //injeta dependencia
    private ProductRepository productRepository;

    /*
    TestConfig existe para testar a conexão entre ambos Repository, que vao chamar os dados, com o proprio
    banco de dados que armazena os dados
    
     */

    @Override //Overide aqui não seria nescessario para a aplicação rodar de maneira correta
    public void run(String... args) throws Exception {


        Category cat1 = new Category(null, "Electronics");
        Category cat2 = new Category(null, "Books");
        Category cat3 = new Category(null, "Computers");

        Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5);
        Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0);
        Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0);
        Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0);
        Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99);

        // tudo que colocar dentro desse metodo íra executar quando a aplicação for inicializada
        User u1 = new User("546389", "maria@gmail.com", "maria fulana", "123456");
        User u2 = new User("546378", "AlexGreen@gmail.com", "Alex Green", "123456");

        Order o1 = new Order(null, OrderStatus.PAID, u1, Instant.parse("2019-06-20T19:53:07Z"));
        Order o2 = new Order(null, OrderStatus.WAITING_PAYMENT, u2, Instant.parse("2019-07-21T03:42:10Z"));
        Order o3 = new Order(null, OrderStatus.WAITING_PAYMENT, u1, Instant.parse("2019-07-22T15:21:22Z"));

        userRepository.saveAll(Arrays.asList(u1, u2));
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));
        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

        /*
        saveAll(Arrays.asList() --> Indica que os objetos de todas essas classes como: produto, usuario, categoria e pedido
        devem ser salvas no banco de dados local
         */
    }
}
