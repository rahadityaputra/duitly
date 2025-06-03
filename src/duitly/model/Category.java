package duitly.model;

public class Category {
    private int id;
    private int userId;
    private String name;
    private TransactionType type;
    
    public Category() {
        
    }

    public Category(int id, int userId, String name, TransactionType type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
    }

    public Category(String name, TransactionType type) {
        this.name = name;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public TransactionType getType() {
        return this.type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
    
    @Override
    public String toString () {
        return this.name;
    }
}
