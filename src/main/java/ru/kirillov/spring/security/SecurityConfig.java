package ru.kirillov.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.kirillov.spring.config.handler.LoginSuccessHandler;

// настройка секьюрности по определенным URL, а также настройка UserDetails и GrantedAuthority

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final LoginSuccessHandler loginUserHandler;  // класс, в котором описана логика перенаправления пользователей по ролям

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, LoginSuccessHandler loginUserHandler) {
        this.userDetailsService = userDetailsService;
        this.loginUserHandler = loginUserHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // указываем страницу с формой логина
//                .loginPage("/login")                         // убираем, т.к. используем стандартную форму
                //указываем логику обработки при логине
                .successHandler(loginUserHandler)
                // указываем action с формы логина
//                .loginProcessingUrl("/login")                // убираем, т.к. используем стандартную форму
//                // Указываем параметры логина и пароля с формы логина
//                .usernameParameter("j_username")             // убираем, т.к. используем стандартную форму
//                .passwordParameter("j_password")             // убираем, т.к. используем стандартную форму
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать logout всем
                .permitAll()
                // указываем URL logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном logout
                .logoutSuccessUrl("/login")
                //выключаем кроссдоменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable();

        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                //страница аутентификации доступна всем
                .antMatchers("/login").anonymous()
                // защищенные URL
                .antMatchers("/user/**").access("hasAnyRole('ADMIN','USER')")                      // будет "доделка" в ROLE_ADMIN, ROLE_USER
                .antMatchers("/admin/**").access("hasRole('ADMIN')").anyRequest().authenticated(); // будет "доделка" в ROLE_ADMIN
    }
}
