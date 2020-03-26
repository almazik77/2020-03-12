package ru.itis.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
//        http.csrf().disable();

//        http.authorizeRequests()
//                .antMatchers("/signUp").permitAll()
//                .antMatchers("/users").hasAuthority("ADMIN")
//                .antMatchers("/profile").authenticated()
//                .antMatchers("/").authenticated();

        http.authorizeRequests()
                .and()
                .formLogin()
                .loginPage("/signIn")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/files")
                .failureUrl("/signIn?error")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/signIn").permitAll();
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/assets/**")
                .antMatchers("/WEB-INF/css/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**")
                .antMatchers("/fonts/**");
    }
}
