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
        String query = "INSERT INTO categories (user_id, name, type) VALUES (?, ?, ?)";

        try (Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, category.getUserId());
            stmt.setString(2, category.getName());
            stmt.setString(3, category.getType().name());
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category) {
        String query = "UPDATE categories SET name = ?, type = ? WHERE id = ?";

        try (Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category.getName());
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

    public List<Category> getAllCategoriesByUserId(int userId) {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE user_id = ?";

        try (Connection conn = DBConnectionManager.Connect();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));
                categories.add(new Category(id, userId, name, type));
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
                        TransactionType.valueOf(rs.getString("type")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
