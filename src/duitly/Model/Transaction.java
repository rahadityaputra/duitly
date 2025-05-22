package duitly.Model;
import java.math.BigDecimal;
import java.time.LocalDate;;

public class Transaction {
    private int id;
   private String description;
   private BigDecimal amount;
   private LocalDate date;
   private String type;
   private int categoryId;
   private int accountId; 
   
   public Transaction(int id, String description, BigDecimal amount, LocalDate date, String type, int categoryId, int accountId) {
       this.id = id;
       this.description = description;
       this.amount = amount;
       this.date = date;
       this.type = type;
       this.categoryId = categoryId;
       this.accountId = accountId;
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

   public String getType() {
       return this.type;
   }

   public int getCategoryId() {
       return this.categoryId;
   }

   public int getAccountId() {
       return this.accountId;
  
    }
}
