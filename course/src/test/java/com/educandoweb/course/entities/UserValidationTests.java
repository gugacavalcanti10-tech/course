package com.educandoweb.course.entities;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidationTests {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    @AfterAll
    static void closeFactory() {
        factory.close();
    }

    @Test
    void acceptsCompleteUserWithValidEmailAndPassword() {
        assertTrue(validator.validate(new User("11999999999", "ana@exemplo.com", "Ana", "Abcdefgh1!")).isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void requiresAllUserFields(String value) {
        User user = new User(value, value, value, value);
        for (String field : new String[]{"name", "email", "phone", "password"}) {
            assertTrue(validator.validate(user).stream()
                    .anyMatch(error -> error.getPropertyPath().toString().equals(field)));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"1133334444", "11999999999", "(11) 3333-4444", "(11) 99999-9999",
            "(21)33334444", "21 99999-9999"})
    void acceptsPhoneWithAreaCodeAndEightOrNineDigitNumber(String phone) {
        assertTrue(validator.validateValue(User.class, "phone", phone).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"33334444", "999999999", "113333444", "119999999999", "(01) 3333-4444",
            "(1) 3333-4444", "(111) 3333-4444", "(11 3333-4444", "11) 3333-4444",
            "+55 (11) 99999-9999", "(11) abcd-4444", "(11) 3333-4444\n"})
    void rejectsPhoneWithInvalidFormatOrDigitCount(String phone) {
        assertTrue(!validator.validateValue(User.class, "phone", phone).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ana", "ana@", "@exemplo.com", "ana exemplo@dominio.com"})
    void rejectsInvalidEmail(String email) {
        assertTrue(!validator.validateValue(User.class, "email", email).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Abcdefg1!", "abcdefgh1!", "Abcdefghi!", "Abcdefghi1", "Abcdefgh1 "})
    void rejectsPasswordMissingRequiredRule(String password) {
        assertTrue(!validator.validateValue(User.class, "password", password).isEmpty());
    }
}
