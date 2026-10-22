package com.educandoweb.course.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //valor gerado automaticamente
    private Long id;
    private String name;
    private Double price;
    private String imgUrl;

    @Transient // usado para o atributo passar em branco pelo JPA(não serr mapeado)
    private Set<Category> categries = new HashSet<>(); //representa connjunto

    public Product() {
    }

    public Product(Long id, String imgUrl, String name, Double price) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.price = price;
        this.name = name;
    }

    /*
    São adicionados apenas os getters, sem os setters, porque sem eles eu não consigo
    substituir a lista inteira. Usamos apenas os getters para acessar a lista.
     */

    public Long getId() {
        return id;
    }

    public Set<Category> getCategries() {
        return categries;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    /*
    Uma curiosidade, é que o metodo de hascode e equals normalmente e usado em atributos
    identificadores, porque eles não pode ser semelhante aos de outros objetos
    da lista na qual eles pertecem
     */
}
