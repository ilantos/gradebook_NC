package lab3.gradebook.nc.model.db;

import lab3.gradebook.nc.model.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                subject = new Subject(id, title, description, daoLesson.getLessonsBySubjectId(idSubject));
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get subject with id = " + idSubject, e);
        }
        return subject;
    }

    public void edit(Subject subject) throws DAOException {
        try {
            String query =
                    "UPDATE subject" +
                            "   SET title=?" +
                            "   SET description=?" +
                            " WHERE id_subject=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, subject.getTitle());
            statement.setString(2, subject.getDescription());
            statement.setInt(3, subject.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot update subject with id = " + subject.getId(), e);
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
}
