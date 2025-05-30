package duitly.model;

public class Category {
    private int id;
    private int userId;
    private String name;
    private String description;
    private TransactionType type;

    public Category(int id, int userId, String name, String description, TransactionType type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public Category(String name, String description, TransactionType type) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return this.description;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
