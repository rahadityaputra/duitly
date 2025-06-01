package duitly.controller;

import duitly.dao.CategoryDAO;
import duitly.exception.CategoryException;
import duitly.exception.UserException;
import duitly.model.Category;
import duitly.model.User;

import java.util.List;

public class CategoryController {
    private CategoryDAO categoryDAO;
    private User currentUser;

    public CategoryController(User user) {
        this.categoryDAO = new CategoryDAO();
        this.currentUser = user;
    }

    public void addCategory(Category category) {
        if (currentUser != null) {
            category.setUserId(currentUser.getId());
            categoryDAO.insertCategory(category);
        } else {
            throw new UserException("User not found");
        }
    }

    public void updateCategory(Category category) {
        if (currentUser != null) {
            category.setUserId(currentUser.getId());
            categoryDAO.updateCategory(category);
        } else {
            throw new UserException("User not found");
        }
    }

    public void deleteCategory(int id) {
        try {
            categoryDAO.deleteCategory(id);
        } catch (Exception e) {
            throw new UserException("Failed to delete category: " + e.getMessage(), e);
        }
    }

    public List<Category> getAllCategories() {
        try {
            return categoryDAO.getAllCategories();
        } catch (Exception e) {
            throw new CategoryException("Failed to retrieve categories: " + e.getMessage(), e);
        }
    }

    public Category getCategoryById(int id) {
        try {
            return categoryDAO.getCategoryById(id);
        } catch (Exception e) {
            throw new CategoryException("Failed to get category: " + e.getMessage(), e);
        }
    }
}

