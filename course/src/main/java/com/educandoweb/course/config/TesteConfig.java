package com.educandoweb.course.config;

import com.educandoweb.course.entities.*;
import com.educandoweb.course.repositories.*;
import com.educandoweb.course.entities.enums.OrderStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration // anotação que indica que a classe participa da connfiguraçãõ do Spring
@Profile("test") // determina que essa configuração será utilizada quando o perfil test estiver ativo
public class TesteConfig implements CommandLineRunner {

    /*
     * Classe que implementa a interface CommandLineRunner.
     * Ela permite executar ações assim que a aplicação é inicializada.
     * Neste caso, vamos inserir dados de teste no banco de dados.
     */

    @Autowired // Injeta a dependência do repositório de usuários
    private UserRepository userRepository;

    @Autowired // Injeta a dependência do repositório de pedidos
    private OrderRepository orderRepository;

    @Autowired // Injeta a dependência do repositório de categorias
    private CategoryRepository categoryRepository;

    @Autowired // Injeta a dependência do repositório de produtos
    private ProductRepository productRepository;

    @Autowired // injeta dependência do repositório OrderItem
    private OrderItemRepository orderItemRepository;

    /*
     * A classe TesteConfig utiliza os repositórios para inserir
     * dados iniciais no banco de dados de teste.
     */

    @Override
    public void run(String... args) throws Exception {

        // 1. Criação das categorias
        Category cat1 = new Category(null, "Electronics");
        Category cat2 = new Category(null, "Books");
        Category cat3 = new Category(null, "Computers");

        // 2. Criação dos produtos
        Product p1 = new Product(null, "Lorem ipsum dolor sit amet, consectetur.", "The Lord of the Rings", 90.5);
        Product p2 = new Product(null, "Nulla eu imperdiet purus. Maecenas ante.", "Smart TV", 2190.0);
        Product p3 = new Product(null, "Nam eleifend maximus tortor, at mollis.", "Macbook Pro", 1250.0);
        Product p4 = new Product(null, "Donec aliquet odio ac rhoncus cursus.", "PC Gamer", 1200.0);
        Product p5 = new Product(null, "Cras fringilla convallis sem vel faucibus.", "Rails for Dummies", 100.99);

        // 3. Criação dos usuários
        User u1 = new User("546389", "maria@gmail.com", "maria fulana", "123456");
        User u2 = new User("546378", "AlexGreen@gmail.com", "Alex Green", "123456");

        // 4. Criação dos pedidos
        Order o1 = new Order(null, OrderStatus.PAID, u1, Instant.parse("2019-06-20T19:53:07Z"));
        Order o2 = new Order(null, OrderStatus.WAITING_PAYMENT, u2, Instant.parse("2019-07-21T03:42:10Z"));
        Order o3 = new Order(null, OrderStatus.WAITING_PAYMENT, u1, Instant.parse("2019-07-22T15:21:22Z"));

        // 5. Criação dos OrderItem
        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
        OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
        OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
        OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());

        // 6. Salva os usuários primeiro
        userRepository.saveAll(Arrays.asList(u1, u2));

        // 7. Salva as categorias antes de associá-las aos produtos
        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));

        // 8. Associa as categorias aos produtos
        p1.getCategries().add(cat2);

        p2.getCategries().add(cat1);
        p2.getCategries().add(cat3);

        p3.getCategries().add(cat3);

        p4.getCategries().add(cat3);

        p5.getCategries().add(cat2);

        // 9. Salva os produtos com suas associações às categorias
        productRepository.saveAll(
                Arrays.asList(p1, p2, p3, p4, p5));

        // 10. Salva os pedidos, cujos usuários já foram persistidos
        orderRepository.saveAll(Arrays.asList(o1, o2, o3));

        // 11. Salva os itens do pedido
        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

        /*
         * Arrays.asList() organiza os objetos em uma lista.
         * saveAll() recebe essa lista e solicita ao repositório
         * que persista todos os objetos no banco de dados.
         */

        Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
        o1.setPayment(pay1);
        /*

         */

        orderRepository.save(o1);
    }
}
