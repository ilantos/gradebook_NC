package lab3.gradebook.nc.model.db;

import lab3.gradebook.nc.model.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaoPerson {
    private Connection connection;

    @Autowired
    public DaoPerson(Connection connection) {
        this.connection = connection;
    }

    public List<Person> getPeoples() throws DAOException {
        List<Person> personList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM person;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                personList.add(
                        new Person(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getBoolean(9)
                        )
                );
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return personList;
    }

    public Person getById(int idPerson) throws DAOException {
        Person person = null;
        try {
            String query = "SELECT * FROM person" +
                    " WHERE id_person = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idPerson);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                person = new Person(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getBoolean(9)
                );
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return person;
    }

    public Person getByLogin(String login) throws DAOException {
        Person person = null;
        try {
            String query = "SELECT * FROM person" +
                    " WHERE login = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                person = new Person(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getBoolean(9)
                );
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return person;
    }

    public void edit(Person person) throws DAOException {
        try {
            String query = "UPDATE person" +
                            "  SET first_name=?, last_name=?, patronymic=?" +
                            "WHERE id_person=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getPatronymic());
            statement.setInt(4, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void delete(int id) throws DAOException {
        try {
            String query = "DELETE FROM person WHERE id_person=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void add(Person person) throws DAOException {
        try {
            String query =
                    "INSERT INTO person (id_location, first_name, last_name, patronymic, login, password, email, is_admin)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, person.getIdLocation());
            statement.setString(2, person.getFirstName());
            statement.setString(3, person.getLastName());
            statement.setString(4, person.getPatronymic());
            statement.setString(5, person.getLogin());
            statement.setString(6, person.getPassword());
            statement.setString(7, person.getLogin());
            statement.setBoolean(8, person.isAdmin());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
