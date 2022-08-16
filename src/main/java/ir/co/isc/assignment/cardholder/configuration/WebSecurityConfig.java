package ir.co.isc.assignment.cardholder.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**")
                .antMatchers("/swagger-ui/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .cors().disable()
                .httpBasic()
                .and()
                .authorizeRequests().antMatchers("/h2-console**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/swagger-ui/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/**").permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$10$6C1mM8kdZllCBv5YCqZEauNyp1YmdJdmpSt0MKrNFQ7JQnbJyihe.") //pass = 123
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$10$6C1mM8kdZllCBv5YCqZEauNyp1YmdJdmpSt0MKrNFQ7JQnbJyihe.") //pass = 123
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}