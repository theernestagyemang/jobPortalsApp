/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos;

/**
 * @author Admin
 */


import com.debusey.smart.pos.smartpos.db.MyDateobj;
import com.debusey.smart.pos.smartpos.service.MySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableCaching
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private EntityManagerFactory bentityManager;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public MyDateobj createDateObj() {
        return new MyDateobj();
    }

    @Bean
    MySearchService mySearh() {
        MySearchService hibernateSearchService = new MySearchService(bentityManager);
        // hibernateSearchService.initializeHibernateSearch();
        return hibernateSearchService;
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

        //  auth.inMemoryAuthentication().withUser("admin").password("pass").roles("ADMIN");

    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("addresses");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //
    @Bean
    public AuthenticationManager authManager() throws Exception {
        return this.authenticationManager();
    }

    //    @Bean
//    public PasswordEncoder encoder() {
//         return new BCryptPasswordEncoder();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();


        // The pages does not require login
        http.authorizeRequests().antMatchers("/", "/login", "/logout", "/recover", "/recover-pass", "/failed", "/success").permitAll();

        // /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
        // If no login, it will redirect to /login page.
        http.authorizeRequests()
                .antMatchers("/resources/**", "/js/**", "/mycss/**", "/css/**", "/static/**").permitAll();

        //AMIN
        http.authorizeRequests().antMatchers("/users", "/admin", "/employee", "/staff", "/setup-contact", "/adverts",
                "/setup-address", "/duplicate-seeker",
                "/fees", "/subscription", "/uploadService", "/job-categories").hasRole("ADMIN");

        //COMPANY
        http.authorizeRequests().antMatchers("/company-post-jobs", "/company-transactions", "/company-resume", "/company-manage-job", "/company-profile", "/emp-shortlist-candidates", "/update-employer-profile",
                "/comp-shortlisted-applicants", "/candidate-cv", "/com-job-details", "/find-jobseekers", "/view-seeker-profile", "/company-dashboard", "/payment/package-subscription/{id}",
                "/comp-subscription").hasAnyRole("ADMIN", "COMPANY", "ROLE_COMPANY");

        //JOB SEEKER
        http.authorizeRequests().antMatchers("/my-profile", "/payment/assessment/{id}", "/payment/training/{id}","/payment/service/request/{id}", "/payment/course/{id}", "/my-resume", "/saved-jobs", "/jobs-alerts", "/my-cv-manager", "/applied-job", "/save-jobs", "/apply-saved-job", "/personal-details",
                "/free-job-alerts", "/add-testimony", "/seeker-testimonial", "/seeker-profile-cv", "/job-alert-list", "/manage-jobs", "/profile", "/resume", "/cover-letter", "/candidate-dashboard",
                "/profile-summary", "/resume-headline", "/key-skills", "/employment",
                "/education", "/it-skills", "/projects", "/accomplishments", "/desired-career-profile", "/job-preference",
                "/personal-detailsx", "/attach-resume", "/awards",
                "/apply-for-job").hasAnyRole("ADMIN", "ROLE_JOBSEEKER", "JOBSEEKER", "RECRUITER", "ROLE_HEAD_RECRUITER", "HEAD_RECRUITER");


        http.authorizeRequests().antMatchers("/change-password", "/post-job").hasAnyRole("ADMIN", "ROLE_JOBSEEKER", "JOBSEEKER", "RECRUITER", "ROLE_HEAD_RECRUITER", "HEAD_RECRUITER", "COMPANY", "ROLE_COMPANY");
        http.authorizeRequests().antMatchers("/candidates", "/candidate-profile", "/applied-applicants").hasAnyRole("ADMIN", "RECRUITER", "ROLE_HEAD_RECRUITER", "HEAD_RECRUITER", "COMPANY", "ROLE_COMPANY");


        //HEAD_RECRUITER
        http.authorizeRequests().antMatchers("/posted-jobs", "/recruiter-assigned-job", "/assignment-list", "/adverts", "/company-settings", "/settings").hasAnyRole("ADMIN", "ROLE_HEAD_RECRUITER", "HEAD_RECRUITER");

        //ROLE_RECRUITER
        http.authorizeRequests().antMatchers("/assigned-posted-jobs", "/recruiter-assigned-job", "/assigned-posted-jobs","/recruiter/service/requested", "/browse-candidates", "/rec-post-jobs", "/jobs-alerts-ls", "/search-seeker",
                "/job-detail", "/seeker-profile-cv", "/assignment-list", "/work-shop", "/create-work-shop", "/employers", "/view-posted-jobs", "/jobs",
                "/all-job-seekers", "/browse-by-date", "/browse-by-shortlisted", "/find-job-seeker", "/search", "/rec-profile", "/rec-contact", "/resume-request-ls", "/adverts",
                "/seeker-profile", "/seeker-resume", "/seeker-profile", "/shortlisted-candidate").hasAnyRole("ADMIN", "ROLE_RECRUITER", "RECRUITER", "ROLE_HEAD_RECRUITER", "HEAD_RECRUITER");


        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/welcome")//
                .failureUrl("/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout")

                .logoutSuccessUrl("/logoutSuccessful")
                .and()
                .rememberMe()
                .and()

                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/seeker/**").hasAnyRole("ADMIN", "JOBSEEKER", "ROLE_JOBSEEKER")
                .antMatchers("/recruiter/**").hasAnyRole("ADMIN", "RECRUITER", "ROLE_RECRUITER", "HEAD_RECRUITER", "ROLE_HEAD_RECRUITER")
                .antMatchers("/public/**").hasAnyRole("ADMIN", "RECRUITER", "ROLE_RECRUITER", "HEAD_RECRUITER", "ROLE_HEAD_RECRUITER", "COMPANY", "ROLE_COMPANY")
                .antMatchers("/rhead/**").hasAnyRole("ADMIN", "HEAD_RECRUITER", "ROLE_HEAD_RECRUITER")
                .antMatchers("/employer/**").hasAnyRole("ADMIN", "COMPANY", "ROLE_COMPANY")
                .antMatchers("/accounts/**").hasAnyRole("ADMIN", "ACCOUNTS", "ROLE_ACCOUNTS");
        // Config for Login Form


    }

}
