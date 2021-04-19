package lab3.gradebook.nc.model.db;

import lab3.gradebook.nc.model.entities.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DaoLesson {
    private Connection connection;

    @Autowired
    public DaoLesson(Connection connection) {
        this.connection = connection;
    }
    public List<Lesson> getLessonsBySubjectId(int idSubject) throws DAOException {
        List<Lesson> lessons = new ArrayList<>();
        try {
            String query = "SELECT * FROM lesson"
                    + " WHERE id_subject=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idSubject);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(3);
                String description = resultSet.getString(4);
                int maxGrade = resultSet.getInt(5);
                LocalDateTime creationDate = resultSet.getTimestamp(6).toLocalDateTime();
                lessons.add(new Lesson(id, title, description, maxGrade, creationDate));
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get all locations", e);
        }
        return lessons;
    }

    public Lesson getById(int idLesson) throws DAOException {
        Lesson lesson = null;
        try {
            String query = "SELECT * FROM lesson"
                    + " WHERE id_lesson=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idLesson);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(3);
                String description = resultSet.getString(4);
                float max_grade = resultSet.getInt(5);
                LocalDateTime creation_date = resultSet.getTimestamp(6).toLocalDateTime();
                lesson = new Lesson(id, title, description, max_grade, creation_date);
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get lesson with id = " + idLesson, e);
        }
        return lesson;
    }

    public void edit(Lesson lesson) throws DAOException {
        try {
            String query =
                    "UPDATE lesson" +
                            "   SET title=?," +
                            "   description=?," +
                            "   max_grade=?" +
                            " WHERE id_lesson=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, lesson.getTitle());
            statement.setString(2, lesson.getDescription());
            statement.setFloat(3, lesson.getMaxGrade());
            statement.setInt(4,  lesson.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot update subject with id = " + lesson.getId(), e);
        }
    }

    public void delete(int id) throws DAOException {
        try {
            String query = "DELETE FROM lesson" + " WHERE id_lesson=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot get all subjects", e);
        }
    }
    public void add(Lesson lesson) throws DAOException {
        try {
            String query =
                    "INSERT INTO lesson(" +
                            "title, description, max_grade)" +
                            "VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, lesson.getTitle());
            statement.setString(2, lesson.getDescription());
            statement.setFloat(2, lesson.getMaxGrade());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot get all lessons", e);
        }
    }
}
