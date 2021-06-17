package lab3.gradebook.nc.model.db;

import lab3.gradebook.nc.model.StudyRole;
import lab3.gradebook.nc.model.entities.Lesson;
import lab3.gradebook.nc.model.entities.Person;
import lab3.gradebook.nc.model.entities.ScheduleEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
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
            String query = "SELECT * FROM person"
                    + " WHERE id_person = ?;";
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

    public Person getTeacherOfSubject(int id) throws DAOException {
        Person person = null;
        try {
            String query = "select p.* "
                    + "  from person p "
                    + "  join person_subject ps on p.id_person = ps.id_person"
                    + " where ps.id_subject = ?"
                    + "and ps.person_role = 'TEACHER';";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
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
            String query = "SELECT * FROM person"
                    + " WHERE login = ?;";
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
            String query = "UPDATE person"
                            + "  SET first_name=?, last_name=?, patronymic=?"
                            + "WHERE id_person=?;";
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
                    "INSERT INTO person (id_location, first_name, last_name,"
                            + "patronymic, login, password, email, is_admin)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
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

    public List<ScheduleEntry> getSchedule(String username)
            throws DAOException {
        List<ScheduleEntry> scheduleList = new ArrayList<>();
        try {
            String query = "SELECT s.title, l.*, ps.person_role, s.id_subject "
                    + "FROM person p "
                    + "JOIN person_subject ps on p.id_person = ps.id_person "
                    + "JOIN subject s on ps.id_subject = s.id_subject "
                    + "JOIN lesson l on s.id_subject = l.id_subject "
                    + "WHERE p.login = ? "
                    + "ORDER BY l.start_date;";
            PreparedStatement preparedStatement
                    = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_lesson");
                String subjectTitle = resultSet.getString(1);
                String lessonTitle = resultSet.getString(4);
                String description = resultSet.getString("description");
                float maxGrade = resultSet.getFloat("max_grade");
                LocalDateTime creationDate = resultSet
                        .getTimestamp("creation_date").toLocalDateTime();
                LocalDateTime startDate = resultSet
                        .getTimestamp("start_date").toLocalDateTime();
                Lesson lesson = new Lesson(id,
                        lessonTitle,
                        description,
                        maxGrade,
                        creationDate,
                        startDate
                );
                StudyRole role = StudyRole.valueOf(resultSet
                        .getString("person_role"));
                int subjectId = resultSet.getInt("id_subject");
                scheduleList.add(
                        new ScheduleEntry(
                                subjectId,
                                subjectTitle,
                                lesson,
                                role));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return scheduleList;
    }
}
