package lab3.gradebook.nc.config;

import lab3.gradebook.nc.model.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {
        "lab3.gradebook.nc.controllers",
        "lab3.gradebook.nc.config",
        "lab3.gradebook.nc.model"
})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //http.authorizeRequests().anyRequest().permitAll();
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/sign-up*").permitAll()
                .antMatchers("/registration*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginPage("/login.html")
                .defaultSuccessUrl("/locations")
                .failureUrl("/login")
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");

//        http.authorizeRequests()
//                .antMatchers("/location/**").authenticated()
//                .antMatchers("/api/locations/**").permitAll()
//                .antMatchers("/locations").permitAll()
//                .and().httpBasic();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
