package lab3.gradebook.nc.config;

import lab3.gradebook.nc.model.db.DAOException;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
    @Value("${http.server}")
    private String server;

    @Primary
    @Bean(name = "dataSource", destroyMethod = "")
    @Profile("weblogic")
    public DataSource dataSourceWeblogic() throws NamingException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        InitialContext ctx = (InitialContext) jndiTemplate.getContext();
        return  (javax.sql.DataSource) ctx.lookup("gradebook_postgresql");
    }

    private Connection getConnection() throws DAOException {
        Connection connection;
        Properties props = new Properties();
        props.setProperty("user", this.username);
        props.setProperty("password", this.password);
        try {
            Class.forName(this.driverClass);
            connection = DriverManager.getConnection(this.url, props);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return connection;
    }

    @Bean
    public Connection connection()
            throws DAOException, NamingException, SQLException {
        switch (server) {
            case "tomcat":
                return dataSourceWeblogic().getConnection();
            case "remote-weblogic":
                return getConnection();
            default: throw new AssertionError(
                    "Isn't available http server");
        }
    }

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(this.url);
        dataSource.setUser(this.username);
        dataSource.setPassword(this.password);
        return dataSource;
    }
}
