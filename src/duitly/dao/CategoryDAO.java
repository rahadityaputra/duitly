package duitly.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import duitly.database.DBConnectionManager;
import duitly.model.Category;
import duitly.model.TransactionType;

public class CategoryDAO {

    public void insertCategory(Category category) {
        String query = "INSERT INTO categories (name, description, type) VALUES (?, ?, ?)";

        try (Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category.getName());
            stmt.setString(1, category.getDescription());
            stmt.setString(2, category.getType().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category) {
        String query = "UPDATE categories SET name = ?, type = ?, description = ? WHERE id = ?";

        try (Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category.getName());
            stmt.setString(1, category.getDescription());
            stmt.setString(2, category.getType().name());
            stmt.setInt(3, category.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int id) {
        String query = "DELETE FROM categories WHERE id = ?";

        try (Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";

        try (Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));

                categories.add(new Category(id, userId, name, description, type));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public Category getCategoryById(int id) {
        String query = "SELECT * FROM categories WHERE id = ?";
        try (Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Category(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        TransactionType.valueOf(rs.getString("type")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
