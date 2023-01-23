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
    	//未ログインユーザーも遷移可能な画面をまとめた処理
        http.authorizeRequests()
        		//以下のurlに対しては、未ログインユーザーでもアクセスを許可する
                .antMatchers("/loginForm").permitAll()//ログイン画面
                .antMatchers("/users/new").permitAll()//ユーザー新規作成画面
                .antMatchers("/users/save").permitAll()//ユーザー作成処理
                .antMatchers("/threads").permitAll()//スレッド一覧画面
                .antMatchers("/categories").permitAll()//カテゴリー一覧画面
                .antMatchers("/threads/threadsCategory/{id}").permitAll()//カテゴリー一覧>カテゴリー>スレッド

                .anyRequest().authenticated();

        http.formLogin()
        .loginProcessingUrl("/login")//ログイン処理のパス
                .loginPage("/loginForm")//ログイン画面表示するページの設定
                .usernameParameter("email")//ログイン画面のメールアドレス
                .passwordParameter("password")//ログイン画面のパスワード
                .defaultSuccessUrl("/threads", true)//ログイン成功時遷移先
                .failureUrl("/loginForm?error");//ログイン失敗時の遷移先

        //ログアウトに関する処理
        http.logout()
        .logoutUrl("/logout")//ログアウト処理時のパス
        .logoutSuccessUrl("/loginForm");//ログアウト後の遷移先


    }
}