package duitly.controller;

import duitly.model.Category;
import duitly.model.Transaction;
import duitly.model.User;

import java.util.List;

public class MainController {
    private UserController userController;
    private TransactionController transactionController;
    private CategoryController categoryController;

    public MainController() {
        this.userController = new UserController();
    }

    public boolean login(String username, String password) {
        userController.login(username, password);
        User user = userController.getCurrentUser();
        if (user != null) {
            this.transactionController = new TransactionController(user);
            this.categoryController = new CategoryController(user);
            return true;
        }
        return false;
    }

    public void register(String username, String password, String fullname, String email) {
        userController.register(username, password, fullname, email);
    }

    public void logout() {
        userController.logout();
        this.transactionController = null;
    }

    public User getCurrentUser() {
        return userController.getCurrentUser();
    }

    public void addTransaction(Transaction transaction) {
        if (transactionController != null) {
            transactionController.addTransaction(transaction);
        }
    }

    public void updateTransaction(Transaction transaction) {
        if (transactionController != null) {
            transactionController.updateTransaction(transaction);
        }
    }

    public void deleteTransaction(int transactionId) {
        if (transactionController != null) {
            transactionController.deleteTransaction(transactionId);
        }
    }

    public List<Transaction> getAllTransactions() {
        if (transactionController != null) {
            return transactionController.getAllTransactions();
        }
        return null;
    }

    public List<Category> getAllCategories() {
        if (categoryController != null) {
            return categoryController.getAllCategories();
        }
        return null;
    }

    public void addCategory(Category category) {
        if (categoryController != null) {
            categoryController.addCategory(category);
        }
    }

    public void updateCategory(Category category) {
        if (categoryController != null) {
            categoryController.updateCategory(category);
        }
    }

    public void deleteCategory(int id) {
        if (categoryController != null) {
            categoryController.deleteCategory(id);
        }
    }
}
