package duitly.controller;

import duitly.model.User;
import duitly.dao.UserDAO;
import duitly.exception.UserException;

public class UserController {
    private User currentUser;
    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    public void login(String username, String password) {
        try {
            boolean isUserRegistered = userDAO.checkUserExists(username, password);
            if (isUserRegistered) {
                boolean isUserValidated = userDAO.isUserValidated(username, password);
                if (isUserValidated) {
                    System.out.println("Login successful!");
                    currentUser = userDAO.getUser(username);
                } else {
                    throw new UserException("Login failed: Incorrect password. Please try again.");
                }
                
            } else {
                throw new UserException("Login failed: User not found.");
            }
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void register(String username, String password, String fullname, String email) {
        try {
            boolean isUserRegistered = userDAO.checkUserExists(username, email);
            if (!isUserRegistered) {
                User newUser = new User(username, password, fullname, email);
                userDAO.saveUser(newUser);
                currentUser = newUser;
            } else {
                throw new UserException("Register failed: User already exists.");
            }
        } catch (UserException e) {
            throw e;
        }
    }

    public User getCurrentUser() {
        String name = this.currentUser.getFullname();
        return this.currentUser;
    }

    public void logout() {
        this.currentUser = null;
        System.out.println("User logged out.");
    }

    public void updateProfil(String newName, String newPassword) {

    }
}
