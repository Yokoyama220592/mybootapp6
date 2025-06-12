package jp.te4a.spring.boot.myapp13.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jp.te4a.spring.boot.myapp13.service.LoginUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private LoginUserDetailsService loginUserDetailsSrevice;
    //↓サービスクラスにエンコーダを登録し、パスワードを記録する際に暗号化処理をかけるよう指定
    public void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserDetailsSrevice).passwordEncoder(passwordEncoder());
    }
    @Bean //BCryptアルゴリズムによるハッシュ生成(Pbkdf2の方が新しい。本来は複数切り替えられるようにしておくが、今回は１つだけ使用)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.formLogin(login -> login
            .loginProcessingUrl("/login")   //ログイン処理を行うパス

            .loginPage("/loginForm")        //ログインページのパス

            .defaultSuccessUrl("/books", true)  //ログイン成功時、
            .failureUrl("/loginForm?error")     //失敗時に遷移するパス

            .usernameParameter("username").passwordParameter("password")//ログインに使用するパラメータ

            .permitAll()        //ログイン関係のパスは認証前でも アクセス可能とする      

        ).logout(logout -> logout               //ログアウトした時に
            .logoutSuccessUrl("/loginForm"))    //遷移するパス


        .authorizeHttpRequests(authz -> authz

            //全ユーザがアクセス可能なパス
            .requestMatchers("/webjars/**", "/css/**").permitAll()
            .requestMatchers("/loginForm").permitAll()
            .requestMatchers("/users").permitAll()
            .requestMatchers("/users/create").permitAll()

            //上記以外は認証が必要
            .anyRequest().authenticated()
        );
        return http.build();
    }
}