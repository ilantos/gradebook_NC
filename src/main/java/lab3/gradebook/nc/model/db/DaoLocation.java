package lab3.gradebook.nc.model.db;

import lab3.gradebook.nc.model.entities.LocationType;
import lab3.gradebook.nc.model.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DaoLocation {
    private Connection connection;

    @Autowired
    public DaoLocation(Connection connection) {
        this.connection = connection;
    }

    public List<Location> getAll() throws DAOException {
        List<Location> locationList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM location;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int parentId = resultSet.getInt(2);
                LocationType locationType = LocationType.valueOf(resultSet.getString(3));
                String title = resultSet.getString(4);
                locationList.add(new Location(id, parentId, title, locationType));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get all locations", e);
        }
        return locationList;
    }

    public List<Location> getAllWithoutType(LocationType locationType) throws DAOException {
        List<Location> locationList = new ArrayList<>();
        try {
            String query = "SELECT * FROM location WHERE type_loc != ?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, locationType.name(), Types.OTHER);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int parentId = resultSet.getInt(2);
                LocationType locationTypeFromDB = LocationType.valueOf(resultSet.getString(3));
                String title = resultSet.getString(4);
                locationList.add(new Location(id, parentId, title, locationTypeFromDB));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get all locations", e);
        }
        return locationList;
    }

    public Location getById(int idLocation) throws DAOException {
        Location location = null;
        try {
            String query = "SELECT * FROM location"
                    + " WHERE id_location=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idLocation);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int parentId = resultSet.getInt(2);
                LocationType locationType = LocationType.valueOf(resultSet.getString(3));
                String title = resultSet.getString(4);
                location = new Location(id, parentId, title, locationType);
            }
        } catch (SQLException e) {
            throw new DAOException("Cannot get all locations", e);
        }
        return location;
    }

    public List<Location> getParentLocations(int idLocation) throws DAOException {
        List<Location> locationList = new ArrayList<>();
        try {
            String query =
                    "WITH RECURSIVE location_search (id_location, id_parent, type_loc, title)  AS (" +
                            "SELECT * FROM location " +
                            " WHERE id_location = ? " +
                            " UNION ALL " +
                            "SELECT l.*" +
                            "  FROM location_search ls, location l " +
                            " WHERE l.id_location = ls.id_parent) " +
                            "SELECT * FROM location_search;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idLocation);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int parentId = resultSet.getInt(2);
                LocationType locationType = LocationType.valueOf(resultSet.getString(3));
                String title = resultSet.getString(4);
                locationList.add(new Location(id, parentId, title, locationType));
            }

        } catch (SQLException e) {
            throw new DAOException("Cannot get all locations", e);
        }
        return locationList;
    }

    public void edit(Location location) throws DAOException {
        try {
            String query =
                    "UPDATE location" +
                    "   SET id_parent=?, type_loc=?, title=?" +
                    " WHERE id_location=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            if (location.getParentLoc() == 0) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, location.getParentLoc());
            }
            statement.setObject(2, location.getLocationType(), Types.OTHER);
            statement.setString(3, location.getTitle());
            statement.setInt(4, location.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void delete(int id) throws DAOException {
        try {
            String query = "DELETE FROM location" + " WHERE id_location=?;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void add(Location location) throws DAOException {
        try {
            String query =
                    "INSERT INTO location(" +
                    "id_parent, type_loc, title)" +
                    "VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(query);
            if (location.getParentLoc() == 0) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, location.getParentLoc());
            }
            statement.setObject(2, location.getLocationType(), Types.OTHER);
            statement.setString(3, location.getTitle());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
