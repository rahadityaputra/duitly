package duitly.controller;

import duitly.dto.DashboardSummary;
import duitly.exception.UserException;
import duitly.model.Category;
import duitly.model.Transaction;
import duitly.model.User;
import java.math.BigDecimal;

import java.util.List;

public class MainController {
    private UserController userController;
    private TransactionController transactionController;
    private CategoryController categoryController;

    public MainController() {
        this.userController = new UserController();
    }

    public void login(String username, String password) {
        try {
            userController.login(username, password);
            User user = userController.getCurrentUser();
            if (user != null) {
                this.transactionController = new TransactionController(user);
                this.categoryController = new CategoryController(user);
            } else {
                throw new UserException("user not registered");
            }
        } catch (UserException e) {
            throw e;
        }
        
    }

    public void register(String username, String password, String fullname, String email) {
        try {
            userController.register(username, password, fullname, email);
            User user = userController.getCurrentUser();
             if (user != null) {
                this.transactionController = new TransactionController(user);
                this.categoryController = new CategoryController(user);
            } else {
                throw new UserException("user not registered");
            }
        } catch (UserException e) {
            throw e;
        }
    }

    public void logout() {
        try {
            userController.logout();
            this.transactionController = null;
        } catch (Exception e) {
            throw e;
        }
    }

    public User getCurrentUser() {
        return userController.getCurrentUser();
    }

    public void addTransaction(Transaction transaction) {
        try {
            if (transactionController != null) {
            transactionController.addTransaction(transaction);
        }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateTransaction(Transaction transaction) {
        try {
            if (transactionController != null) {
                transactionController.updateTransaction(transaction);
            }
        } catch (Exception e) {
            throw e;
        }
        
    }

    public void deleteTransaction(int transactionId) {
        try {
            if (transactionController != null) {
                transactionController.deleteTransaction(transactionId);
            }
            
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Transaction> getAllTransactions() {
        if (transactionController != null) {
            return transactionController.getAllTransactions();
        }
        return null;
    }
        
    public List<Transaction> getTodayTransactions() {
        return transactionController.fetchTodayTransactions();
    }
    
    public DashboardSummary getDashboardSummary() {
        BigDecimal income = transactionController.getMonthlyIncome();
        BigDecimal expense = transactionController.getMonthlyExpense();
        DashboardSummary dashboardSummary = new DashboardSummary(income, expense);
        return dashboardSummary;
    }

    public List<Category> getAllCategoriesCurrentUser() {
        if (categoryController != null) {
            return categoryController.getAllCategoriesCurrentUser();
        }
        return null;
    }

    public void addCategory(Category category) {
        try {
            if (categoryController != null) {
                categoryController.addCategory(category);
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
}
