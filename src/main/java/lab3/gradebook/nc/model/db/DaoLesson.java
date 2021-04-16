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
}
