package me.kanmodel.july19.onlineteach.adapter;/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: KanModel
 * Date: 2019-04-05-21:55
 */

import me.kanmodel.july19.onlineteach.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description: todo
 * @author: KanModel
 * @create: 2019-04-05 21:55
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启security注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
//        http.csrf().disable()
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/webjars/**", "/post/p**", "/post/list", "/lib/**",
                        "/css/**", "/js/**", "/fonts/**", "/images/**", "/api/**").permitAll()
                .antMatchers("/space/**", "/user/edit/display", "/favorite/**", "/favorite",
                        "/logout", "/user/edit/mpass").authenticated()
                .antMatchers("/manage**", "/swagger-ui.html").hasAnyRole("SUPER")
                .anyRequest().hasAnyRole("ADMIN", "SUPER")
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/space")
                .failureForwardUrl("/passerror")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
