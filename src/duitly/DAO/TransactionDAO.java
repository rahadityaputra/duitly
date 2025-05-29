package duitly.DAO;

import duitly.Database.DBConnectionManager;
import duitly.Model.Transaction;
import duitly.Model.TransactionType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public void insertTransaction(Transaction transaction) {
        String query = "INSERT INTO transactions (description, amount, date) VALUES (?, ?, ?)";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setString(1, transaction.getDescription());
            stmt.setBigDecimal(2, transaction.getAmount());
            stmt.setDate(3, Date.valueOf(transaction.getDate())); // assuming date is LocalDate
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(Transaction transaction) {
        String query = "DELETE FROM transactions WHERE id = ?";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setInt(1, transaction.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTransaction(Transaction transaction) {
        String query = "UPDATE transactions SET description = ?, amount = ?, date = ? WHERE id = ?";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setString(1, transaction.getDescription());
            stmt.setBigDecimal(2, transaction.getAmount());
            stmt.setDate(3, Date.valueOf(transaction.getDate()));
            stmt.setInt(4, transaction.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllTransaction() {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                int id = rs.getInt("id");
                String description = rs.getString("description");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDate date = rs.getDate("date").toLocalDate();
                int categoryId = rs.getInt("categiry_id");
                int userId = rs.getInt("user_id");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));
                Timestamp created_at = rs.getTimestamp("created_at");

                Transaction transaction = new Transaction(id, userId, type, amount, description, date, categoryId,
                        created_at);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
