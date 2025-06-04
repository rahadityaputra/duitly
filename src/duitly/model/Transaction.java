package duitly.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;import java.time.LocalTime;
;

public class Transaction {
    private int id;
    private int userId;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private String categoryName;
    private Timestamp created_at;
    private int categoryId;
    
    public Transaction() {
        
    }

    public Transaction(int id, int userId, int categoryId, String categoryName, TransactionType type, BigDecimal amount, String description, Timestamp created_at) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.userId = userId;
        this.created_at = created_at;
    }


public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }
    public void setAmouunt(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return this.type;
    }
    
     public void setType(TransactionType type) {
        this.type = type;
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
    
    public String getDate() {
        LocalDateTime dateTime = created_at.toLocalDateTime();
        LocalDate tanggal = dateTime.toLocalDate();
        return tanggal.toString();
    }
    
    public String getTime() {
        LocalDateTime dateTime = created_at.toLocalDateTime();
        LocalTime waktu = dateTime.toLocalTime();
        int jam = waktu.getHour();
        int menit = waktu.getMinute();
        return jam + ":" + menit;
        
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
    
     public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
