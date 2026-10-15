package com.educandoweb.course.config;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration // chamada de configuração
@Profile("test")// tras especificidade no que será configurado, delimitação

public class TesteConfig implements CommandLineRunner { // nome da classe

    @Autowired // injetar automaticamente uma dependencia

    private UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        // tudo que colocar dentro desse metodo íra executar quando a aplicação for inicializada
        User u1 = new User("546389", "maria@gmail.com", "maria fulana", "123456");
        User u2 = new User("546378", "AlexGreen@gmail.com", "Alex Green", "123456");

        userRepository.saveAll(Arrays.asList(u1, u2));
    }
}
