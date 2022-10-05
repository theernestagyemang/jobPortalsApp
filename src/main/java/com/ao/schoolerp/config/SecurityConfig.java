package com.ao.schoolerp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    //Authorization method
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/api/v1/create-admin").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/**").authenticated().and().httpBasic();

        //login configuration
        http.authorizeRequests().and().formLogin()
//                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .and()
                .logout().logoutUrl("/logout")
                .and()

                .rememberMe()
               // .forgotpassword("/forgot-password")
                .and()

                .authorizeRequests()
                .antMatchers("/","/home","/admin/**","/library/**","/hostel/**").authenticated();

//                .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN","ROLE_HOSTEL_MANAGER")
//                .antMatchers("/library/**").hasAnyRole("ROLE_LIBRARY_MANAGER")
//                .antMatchers("/hostel/**").hasAnyRole("ROLE_HOSTEL_MANAGER");
    }

    //Authentication method
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.userDetailsService(userDetailsService);
    }

    //Method to encrypt user password
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
