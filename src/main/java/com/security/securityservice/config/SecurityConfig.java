package com.security.securityservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.security.securityservice.security.UserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()     // authorizeRequests and
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()            // permit all white lists urls
                .antMatchers("/api/**").hasAnyRole(STUDENT.name())
                /* .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
                 .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
                 .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
                 .antMatchers("/management/api/**").hasAnyRole(ADMIN.name(),ADMIN_TRAINEE.name())*/
                .anyRequest()          // any request
                .authenticated()        // must be authenticated
                .and()                  // and
                .httpBasic();           // I want to use basic authentication i.e client must be pass username and password and mechanism enforce the authenticity by basic authentication
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails johnSmith = User.builder().username("john")
                .password(passwordEncoder.encode("password"))
                //   .roles(STUDENT.name()) // role internally will be ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails adminUser = User.builder().username("admin")
                .password(passwordEncoder.encode("admin123"))
                //   .roles(ADMIN.name())// role internally will be ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails tom = User.builder().username("tom")
                .password(passwordEncoder.encode("password123"))
                //    .roles(ADMIN_TRAINEE.name()) //role internally will be ROLE_ADMIN_TRAINEE
                .authorities(ADMIN_TRAINEE.getGrantedAuthorities())
                .build();


        return new InMemoryUserDetailsManager(johnSmith, adminUser, tom);

    }
}
