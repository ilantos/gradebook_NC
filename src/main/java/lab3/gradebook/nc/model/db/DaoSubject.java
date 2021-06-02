package lab3.gradebook.nc.model.db;

import lab3.gradebook.nc.model.StudyRole;
import lab3.gradebook.nc.model.entities.SubjectWithGrades;
import lab3.gradebook.nc.model.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DaoSubject {
    private final Connection connection;
    private final DaoLesson daoLesson;

    @Autowired
    public DaoSubject(Connection connection, DaoLesson daoLesson) {
        this.connection = connection;
        this.daoLesson = daoLesson;
    }

    public List<Subject> getAll() throws DAOException {
        List<Subject> subjects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM subject;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                subjects.add(new Subject(id, title, description, null));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get all subjects", e);
        }
        return subjects;
    }

    public List<Subject> getByLoginAndRole(String login, StudyRole role)
            throws DAOException {
        List<Subject> subjects = new ArrayList<>();
        try {
            String query = "SELECT s.*"
                    + "  FROM person p"
                    + "  JOIN person_subject ps ON (ps.id_person = p.id_person)"
                    + "  JOIN subject s ON (s.id_subject = ps.id_subject)"
                    + " WHERE p.login = ? AND ps.person_role = ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setObject(2, role, Types.OTHER);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                subjects.add(new Subject(id, title, description, null));
            }

        } catch (SQLException e) {
            System.out.println(e);
            throw new DAOException(e.getMessage(), e);
        }
        return subjects;
    }

    public boolean isAvailableForUser(int id, String login)
            throws DAOException {
        try {
            String query = "SELECT p.login, ps.id_subject "
                    + "FROM person p "
                    + "JOIN person_subject ps ON (ps.id_person = p.id_person) "
                    + "WHERE p.login = ? "
                    + "  AND ps.id_subject = ? "
                    + "  AND ps.person_role = 'TEACHER';";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public Subject getById(int idSubject) throws DAOException {
        Subject subject = null;
        try {
            String query = "SELECT * FROM subject"
                    + " WHERE id_subject=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idSubject);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                subject = new Subject(
                        id,
                        title,
                        description,
                        daoLesson.getLessonsBySubjectId(idSubject));
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get subject with id = "
                    + idSubject, e);
        }
        return subject;
    }

    public SubjectWithGrades getWithGradesById(int idSubject, String login)
            throws DAOException {
        SubjectWithGrades subject = null;
        try {
            String query = "SELECT * FROM subject"
                    + " WHERE id_subject=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idSubject);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                subject = new SubjectWithGrades(
                        id,
                        title,
                        description,
                        daoLesson.getStudentLessonsBySubjectId(idSubject,
                                login));
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get subject with id = "
                    + idSubject, e);
        }
        return subject;
    }

    public Subject getByLessonId(int idLesson) throws DAOException {
        Subject subject = null;
        try {
            String query = "SELECT s.*"
                    + "FROM lesson l, subject s"
                    + " WHERE l.id_lesson=? AND s.id_subject = l.id_subject;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idLesson);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String description = resultSet.getString(3);
            subject = new Subject(
                    id,
                    title,
                    description,
                    daoLesson.getLessonsBySubjectId(id));
        } catch (SQLException e) {
            throw new DAOException("Cannot get subject with idLesson = "
                    + idLesson, e);
        }
        return subject;
    }

    public void edit(Subject subject) throws DAOException {
        try {
            String query =
                    "UPDATE subject"
                            + "   SET title=?,"
                            + "   description=?"
                            + " WHERE id_subject=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, subject.getTitle());
            statement.setString(2, subject.getDescription());
            statement.setInt(3, subject.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot update subject with id = "
                    + subject.getId(), e);
        }
    }

    public void delete(int id) throws DAOException {
        try {
            String query = "DELETE FROM subject" + " WHERE id_subject=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot get all subjects", e);
        }
    }
    public void add(Subject subject) throws DAOException {
        try {
            String query =
                    "INSERT INTO subject("
                            + "title, description)"
                            + "VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, subject.getTitle());
            statement.setString(2, subject.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot get all subjects", e);
        }
    }
}
