package global.goit.service;

import global.goit.database.Database;
import global.goit.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public long create(String name) {
        validateName(name);
        String sql = "INSERT INTO client (name) VALUES (?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating client failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating client failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating client", e);
            throw new RuntimeException(e);
        }
    }

    public String getById(long id) {
        validateId(id);
        String sql = "SELECT name FROM client WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    throw new RuntimeException("Client with id " + id + " not found");
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving client by id", e);
            throw new RuntimeException(e);
        }
    }

    public void setName(long id, String name) {
        validateId(id);
        validateName(name);
        String sql = "UPDATE client SET name = ? WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setLong(2, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Client with id " + id + " not found");
            }
        } catch (SQLException e) {
            logger.error("Error updating client name", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteById(long id) {
        validateId(id);
        String sql = "DELETE FROM client WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Client with id " + id + " not found");
            }
        } catch (SQLException e) {
            logger.error("Error deleting client by id", e);
            throw new RuntimeException(e);
        }
    }

    public List<Client> listAll() {
        String sql = "SELECT id, name FROM client";
        try (Connection connection = Database.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {

            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                clients.add(new Client(id,name));
            }
            return clients;
        } catch (SQLException e) {
            logger.error("Error listing all clients", e);
            throw new RuntimeException(e);
        }
    }

    private void validateId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be positive");
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("Name must be between 2 and 1000 characters");
        }
    }
}
