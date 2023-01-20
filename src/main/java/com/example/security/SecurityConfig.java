package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/style.css", "/fontawesome/**", "/icon/**", "/css/**", "/js/**", "/pages/**", "/webfonts/**", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
        		//以下のurlに対しては、未ログインユーザーでもアクセスを許可する
                .antMatchers("/loginForm").permitAll()
                .antMatchers("/threads").permitAll()
                .antMatchers("/threads/detail/{id}").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
        .loginProcessingUrl("/login")//ログイン処理のパス
                .loginPage("/loginForm")//ログイン画面表示するページの設定
                .usernameParameter("email")//ログイン画面のメールアドレス
                .passwordParameter("password")//ログイン画面のパスワード
                .defaultSuccessUrl("/threads", true)//ログイン成功時遷移先
                .failureUrl("/loginForm?error");//ログイン失敗時の遷移先

    }
}