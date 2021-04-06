package com.finalproject.shelter.config;

import com.finalproject.shelter.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/","/main","/account/register","/account/login","/css/**","/img/**","/check-email", "/check-email-token",
                        "/email-login", "/check-email-login", "/login-link", "/login-by-email").permitAll()
                .mvcMatchers(HttpMethod.GET, "/profile/*","/category/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/account/login")
                .defaultSuccessUrl("/main", true)
                //.failureUrl("/account/login")
                .permitAll();
        http.logout()
                .logoutSuccessUrl("/main");
        http.rememberMe()
                .userDetailsService(accountService)
                .tokenRepository(tokenRepository());
    }


    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("select username,password,enabled "
//                        + "from account "
//                        + "where username = ?")
//                .authoritiesByUsernameQuery("select u.username,r.name "
//                        + "from user_role ur inner join account u on ur.user_id=u.id "
//                        + "inner join role r on ur.role_id=r.id "
//                        + "where u.username = ?");
//    }
//    //Authentication 로그인 / Authorization 권한

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//        //로그인 요청 시, 입력된 유저 정보와 DB의 회원정보를 비교해 인증된
//        // 사용자인지 체크하는 로직이 정의되어있습니다.
//    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // static resource는 security filter를 적용하지 마라고 설정
    }
}