package duitly.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import duitly.database.DBConnectionManager;
import duitly.model.User;

public class UserDAO {

    public List<User> getAllUser() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement pstmt = conn.prepareStatement(query);) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");
                String email = rs.getString("email");

                User user = new User(id, username, fullname, email);
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        }
    }

    public void saveUser(User user) {
        String query = "INSERT INTO users (username, password, fullname, email) VALUES (?, ?, ?, ?)";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFullname());
            pstmt.setString(4, user.getEmail());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

    public void deleteUser(User user) {
        String query = "DELETE users WHERE id = ?";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setInt(1, user.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

    public void updateUser(User user) {
        String query = "UPDATE users SET username = ?, password = ?, fullname = ?, email = ? WHERE id = ?";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(1, user.getPassword());
            pstmt.setString(1, user.getFullname());
            pstmt.setString(1, user.getEmail());
            pstmt.setInt(1, user.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

    public Boolean checkUsernameExists(String username) {
        String query = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();
            return result.next();

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        }
    }

}