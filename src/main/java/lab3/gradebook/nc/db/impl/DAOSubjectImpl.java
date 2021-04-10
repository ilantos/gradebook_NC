package lab3.gradebook.nc.db.impl;

import lab3.gradebook.nc.db.api.DAOSubjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOSubjectImpl implements DAOSubjects{
    private static DAOSubjects instance;
    private Connection connection;
    private DAOSubjectImpl() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "root");
        props.setProperty("ssl", "true");
        connection = DriverManager.getConnection(url, props);
    }

    public synchronized static DAOSubjects getInstance() {
        if (instance == null) {
            try {
                instance = new DAOSubjectImpl();
            } catch (SQLException e) {
                System.out.println("Can't connect to database");
            }
        }
        return instance;
    }

}
