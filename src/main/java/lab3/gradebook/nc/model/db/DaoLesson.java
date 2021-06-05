package lab3.gradebook.nc.model.db;

import lab3.gradebook.nc.model.entities.Lesson;
import lab3.gradebook.nc.model.entities.Person;
import lab3.gradebook.nc.model.entities.StudentLesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DaoLesson {
    private Connection connection;

    @Autowired
    public DaoLesson(Connection connection) {
        this.connection = connection;
    }

    public List<Lesson> getLessonsBySubjectId(int idSubject)
            throws DAOException {
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
                float maxGrade = resultSet.getFloat(5);
                LocalDateTime creationDate = resultSet
                        .getTimestamp(6)
                        .toLocalDateTime();
                LocalDateTime startDate = resultSet
                        .getTimestamp(7)
                        .toLocalDateTime();
                lessons.add(new Lesson(id, title, description, maxGrade, creationDate, startDate));
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get all locations", e);
        }
        return lessons;
    }

    public List<StudentLesson> getStudentLessonsBySubjectId(
            int idSubject,
            String login) throws DAOException {
        List<StudentLesson> lessons = new ArrayList<>();
        try {
            String query = "select l.*, "
                    + "       p.first_name || ' ' || p.last_name as student, "
                    + "       lg.grade "
                    + "  from lesson l "
                    + "  join lesson_grade lg on (l.id_lesson = lg.id_lesson) "
                    + "  join person p on lg.id_person = p.id_person "
                    + "  where p.id_person = "
                    + "        (select id_person from person where login = ?) "
                    + "    and lg.id_subject = ? ORDER BY l.start_date;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(2, idSubject);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(3);
                String description = resultSet.getString(4);
                int maxGrade = resultSet.getInt(5);
                LocalDateTime creationDate = resultSet
                        .getTimestamp(6)
                        .toLocalDateTime();
                LocalDateTime startDate = resultSet
                        .getTimestamp(7)
                        .toLocalDateTime();
                String student = resultSet.getString(8);
                double grade = resultSet.getDouble(9);
                lessons.add(
                        new StudentLesson(
                                id,
                                title,
                                description,
                                maxGrade,
                                creationDate,
                                startDate,
                                student,
                                grade
                        )
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get all locations", e);
        }
        return lessons;
    }

    public Map<Person, Double> getGradesOfStudents(int idLesson)
            throws DAOException {
        Map<Person, Double> grades = new HashMap<>();
        try {
            String query = "SELECT p.*, lg.grade"
                    + " FROM lesson_grade lg"
                    + " JOIN person p ON (p.id_person = lg.id_person"
                    + "                   AND lg.id_lesson = ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idLesson);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                grades.put(
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
                        ),
                        resultSet.getDouble(10));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return grades;
    }

    public void editGrade(
            int idLesson,
            int idStudent,
            double grade) throws DAOException {
        try {
            String sql = "UPDATE lesson_grade SET grade = ?"
                    + " WHERE id_lesson = ? AND id_person = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, grade);
            statement.setInt(2, idLesson);
            statement.setInt(3, idStudent);
            int result = statement.executeUpdate();
            if (result != 1) {
                throw new DAOException(
                        "Update few rows. Must update only one row");
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
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
                float maxGrade = resultSet.getInt(5);
                LocalDateTime creationDate = resultSet
                        .getTimestamp(6)
                        .toLocalDateTime();
                LocalDateTime startDate = resultSet
                        .getTimestamp(7)
                        .toLocalDateTime();
                lesson = new Lesson(id, title, description, maxGrade, creationDate, startDate);
            }
        } catch (SQLException e) {
            throw new DAOException(
                    "Cannot get lesson with id = " + idLesson, e);
        }
        return lesson;
    }

    public void edit(Lesson lesson) throws DAOException {
        try {
            String query =
                    "UPDATE lesson"
                            + "   SET title=?,"
                            + "   description=?,"
                            + "   max_grade=?,"
                            + "   start_date=?"
                            + " WHERE id_lesson=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, lesson.getTitle());
            statement.setString(2, lesson.getDescription());
            statement.setFloat(3, lesson.getMaxGrade());
            statement.setTimestamp(4, Timestamp.valueOf(lesson.getStartDate()));
            statement.setInt(5,  lesson.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot update subject with id = "
                    + lesson.getId(), e);
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

    public void add(Lesson lesson, int subjectId) throws DAOException {
        try {
            String query =
                    "INSERT INTO lesson("
                            + "id_subject, title, description, max_grade, start_date)"
                            + "VALUES (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, subjectId);
            statement.setString(2, lesson.getTitle());
            statement.setString(3, lesson.getDescription());
            statement.setFloat(4, lesson.getMaxGrade());
            statement.setTimestamp(4, Timestamp.valueOf(lesson.getStartDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cannot get all lessons", e);
        }
    }
}
