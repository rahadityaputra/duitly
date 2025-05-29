package duitly.Controller;

import duitly.DAO.UserDAO;
import duitly.Model.User;

public class UserController {
    private User currentUser;
    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    public void login(String username, String password) {
        try {
            boolean isUserRegistered = userDAO.checkUsernameExists(username);
            if (isUserRegistered) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Login failed: User not found.");
            }
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
        }
    }

    public void register(String username, String password, String fullname, String email) {
        try {
            boolean isUserRegistered = userDAO.checkUsernameExists(username);
            if (!isUserRegistered) {
                User newUser = new User(username, password, fullname, email);
                userDAO.saveUser(newUser);
                System.out.println("Registration successful.");
            } else {
                System.out.println("Registration failed: Username already exists.");
            }
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
        }
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void logout() {
        this.currentUser = null;
        System.out.println("User logged out.");
    }

    public void updateProfil(String newName, String newPassword) {

    }
}
