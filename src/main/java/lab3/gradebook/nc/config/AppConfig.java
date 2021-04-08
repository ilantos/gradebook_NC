package lab3.gradebook.nc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "lab3.gradebook.nc.controllers")
@EnableWebMvc
public class AppConfig {
}
