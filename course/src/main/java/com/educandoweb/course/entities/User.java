package com.educandoweb.course.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_user") //renomeada porque User é uma palavra reservada dentro do banco de dados H2
public class User implements Serializable {

    /*
    Serializable é uma interace dentro de Java que permite com que um objeto seja convertido diretamente em
    uma sequencia de bytes
     */

    private static final long serialVersionsUID = 1L;
    //É um valor de identificação da versão de serialização da classe



    @Id // informa ao JPA que o ID é a chave primaria da entidade
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*
    uma anotação para deteminar como será gerado a
    chave primaria, que nesse caso será auto incrementavel, fica por conta do banci
    */

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "client") // indica que o atributo client existe nessa classe Order e é responsavel
    //por mapear as relações entre eles

    private List<Order> orders = new ArrayList<>(); // nome tb pego no diagrama
    /*
    1-) Essa lista representa a quantidade de pedidos associadas ao cliente em questão.
    2-) A parte do "new ArrayList<>()" cria um novo array por cliente para impedir com que comece com valor nulo.
    3-) Então, quando tiver o objeto User, será possivel acessar a lista de pedidos realizadas no nome dele.
     */

    public User() {

    }

    public User(String phone, String email, String name, String password) {
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null && id.equals(user.id);
    }

        /*
    Aqui são os metodos equals e hasCode que tem como função trabalhar na identificação e comparação da
    logica de objetos da classe User

    PARA QUE SERVE EQUALS?
    serva para identificar se dois objetos dentro da mesma classe apresentam as mesma caractaristicas definidas
    por um criterio da classe
     */

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public List<Order> getOrders() {
        return orders;
    }
}

    /*
    PARA QUE SERVE HASCODE?
    Ele tem como função principal gerar um número auxilador na organização de objetos de uma mesma classe
     */

