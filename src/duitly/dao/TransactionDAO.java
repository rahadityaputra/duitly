package duitly.dao;

import duitly.database.DBConnectionManager;
import duitly.model.Transaction;
import duitly.model.TransactionType;

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
            stmt.setDate(3, Date.valueOf(transaction.getDate())); 
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void deleteTransaction(int transactionId) {
        String query = "DELETE FROM transactions WHERE id = ?";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setInt(1, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
           System.out.println(e.getLocalizedMessage());
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
           System.out.println(e.getLocalizedMessage());
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
            System.out.println(e.getLocalizedMessage());
        }
        return transactions;
    }

    public List<Transaction> getAllTransactionsByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE user_id = ?";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                int id = rs.getInt("id");
                String description = rs.getString("description");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDate date = rs.getDate("date").toLocalDate();
                int categoryId = rs.getInt("categiry_id");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));
                Timestamp created_at = rs.getTimestamp("created_at");

                Transaction transaction = new Transaction(id, userId, type, amount, description, date, categoryId,
                        created_at);
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return transactions;
    }


    public Transaction getTransactionById(int transactionId) {
        String query = "SELECT * FROM transactions WHERE id = ?";
        try (
                Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String description = rs.getString("description");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDate date = rs.getDate("date").toLocalDate();
                int categoryId = rs.getInt("categiry_id");
                TransactionType type = TransactionType.valueOf(rs.getString("type"));
                Timestamp created_at = rs.getTimestamp("created_at");

                Transaction transaction = new Transaction(id, userId, type, amount, description, date, categoryId,
                        created_at);
                return transaction;
            }
            return null;

        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }

    }
    
    public BigDecimal getIncomeThisMonth(int userId) {
        String query = """
            SELECT COALESCE(SUM(amount), 0)
            FROM transactions
            WHERE type = 'INCOME'
            AND MONTH(date) = MONTH(CURRENT_DATE())
            AND YEAR(date) = YEAR(CURRENT_DATE())
            AND user_id = ?
        """;

        try (    Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

         return BigDecimal.ZERO;
    }
    
    public BigDecimal getExpenseThisMonth(int userId) {
        String query = """
            SELECT COALESCE(SUM(amount), 0)
            FROM transactions
            WHERE type = 'EXPENSE'
            AND MONTH(date) = MONTH(CURRENT_DATE())
            AND YEAR(date) = YEAR(CURRENT_DATE())
            AND user_id = ?
        """;

        try (    Connection conn = DBConnectionManager.Connect();
                PreparedStatement stmt = conn.prepareStatement(query);) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

         return BigDecimal.ZERO;
    }
}
