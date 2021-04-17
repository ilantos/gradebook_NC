package lab3.gradebook.nc.config;

import lab3.gradebook.nc.model.db.DAOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DaoConfig {
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.driver.class}")
    private String driverClass;

    @Bean
    public Connection connection() throws DAOException {
        Connection connection;
        Properties props = new Properties();
        props.setProperty("user", this.username);
        props.setProperty("password", this.password);
        try {
            Class.forName(this.driverClass);
            connection = DriverManager.getConnection(this.url, props);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException("Cannot get connection to db", e);
        }
        return connection;
    }
}