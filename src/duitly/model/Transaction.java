package duitly.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;;

public class Transaction {
    private int id;
    private int userId;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private Timestamp created_at;
    private int categoryId;

    public Transaction(int id, int userId, TransactionType type, BigDecimal amount, String description, LocalDate date,
            int categoryId, Timestamp created_at) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.categoryId = categoryId;
        this.userId = userId;
        this.created_at = created_at;
    }


	public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public TransactionType getType() {
        return this.type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
