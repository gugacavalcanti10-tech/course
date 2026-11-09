package com.educandoweb.course.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido, como nome@exemplo.com.")
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "^(?:[1-9][0-9]|\\([1-9][0-9]\\)) ?[0-9]{4,5}-?[0-9]{4}$",
            message = "Informe um telefone com DDD de 2 dígitos e número de 8 ou 9 dígitos, como (11) 3333-4444 ou (11) 99999-9999.")
    private String phone;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 10, message = "A senha deve ter pelo menos 10 caracteres.")
    @Pattern(regexp = "(?s)^(?=.*\\p{Lu})(?=.*[0-9])(?=.*[^\\p{L}\\p{N}\\s]).*$",
            message = "A senha deve conter pelo menos uma letra maiúscula, um caractere especial e um número.")
    private String password;

    public UserCreateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

