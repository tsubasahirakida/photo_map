package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    //以前はWebSecurityConfigurerAdapterを継承する必要があったが5.7で非推奨になった。
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /*このコードはラムダ式
         * http.formLogin()はHttpSecurityのメソッド
        */
        http.formLogin(login -> login
                .loginProcessingUrl("/login")
                //.loginPageでカスタムログインページ表示できる
                .loginPage("/login")
                .defaultSuccessUrl("/posts")
                .failureUrl("/login?error")
                .permitAll()
                ).logout(logout -> logout
                        .logoutSuccessUrl("/"))
                /*↓URLごとの認可設定
                 * authorizeRequests()は非推奨になった。
                 * authorizeHttpRequests()を利用した場合は、
                 * FilterSecurityInterceptorとAccessDecisionManagerが使われる
                */
                .authorizeHttpRequests(authz -> authz
                        // mvcMatchers()とantMatchers()は削除された
                        .requestMatchers("/", "/posts", "/images/**", "/css/**", "/users/new").permitAll()
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/posts/[0-9]+")).permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}