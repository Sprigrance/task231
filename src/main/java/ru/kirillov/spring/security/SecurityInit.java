package ru.kirillov.spring.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//SpringSecurityInitializer - обязателен для не boot-приложения.
//        Кода в нем нет, но требуется для регистрации секьюрити в Спринг-контейнере.

// Благодаря этому Initializer будет запрашиваться форма для ввода логина и пароля
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {
}
