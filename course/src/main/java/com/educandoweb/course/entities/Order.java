package com.educandoweb.course.entities;

import com.educandoweb.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L; //valor gerado automaticamente


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identificador de compra

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant moment; // momento da compra

    private Integer orderStatus; // tipo de parametro e nome do parametro

    @ManyToOne //cardinalidade de muitos para um, "podem ter muitos pedidos por um cliente"
    @JoinColumn(name = "client_id") //define a coluna que armazena a refêrencia ao usuario na tabela de pedidos
    private User client; // "client" faz referencia do objeto "User"


    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();
    /*
    Isso daqui e para representar que a entidade pedido pode ter varios itens conntidos nela

     */

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    public Order() { //construtor vazio

    };

    public Order(Long id, OrderStatus orderStatus, User client, Instant moment) { //construtor com todos os atributos
        super();
        this.id = id;
        setOrderStatus(orderStatus);
        this.client = client;
        this.moment = moment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return OrderStatus.valueOf(orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        if(orderStatus != null) {

            this.orderStatus = orderStatus.getCode();
        }
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
    
    /*
    1- SIGNIFICADO DE CASCADE: permite operações de uma entidade sejam propagadas em outra entidade dependente,
    de maneira automatica. Nesse caso, ao salvar um objeto Order, ele salva seu pagamento junto, porque é algo associado.

    2- DIFERENÇA DE AUTOWIRED: ela fornece o objeto da entidade que foi adicionada a innjeção de depencia, mas não automatiza
    as ações de CRUD feitas em uma para a outra, apenas gera a dependencia entre os objetos de classes
     */
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override //indica que o metodo está sendo sobreescrito, porque ele ja foi usado na classe "User"
    public boolean equals(Object o) { // "(Object o)" indica um argumento qualquer como base de comparação
        if (o == null || getClass() != o.getClass()) return false; //compara o argumento com nulo e se são de classes diferentes
        Order order = (Order) o; //casting  (conversão de tipo)
        return Objects.equals(getId(), order.getId());
        /*
        A classe Objects, do pacote java.util, oferece esse método estático para comparar objetos de forma
        segura quando algum deles pode ser null.
         */
    }

    /*
    metodo equals é usado para identificar se existem dois ou mais objetos da classe que tem elemento igual,
    nesse caso o ID, porque é o elemento identificado de cada objeto da classe
     */

    public Set<OrderItem> getItems() {
        return items;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
    /*
    É mais um metodo de organização e cotrole com maior eficiencia. A regra é:
    se eles são iguais pelo "equals()" precisam ter o mesmo "hascode()"
     */

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }
}
