package duitly.controller;

import duitly.dao.TransactionDAO;
import duitly.exception.UserException;
import duitly.model.Transaction;
import duitly.model.User;

import java.util.List;

public class TransactionController {
    private TransactionDAO transactionDAO;
    private User currentUser;

    public TransactionController(User currentUser) {
        this.transactionDAO = new TransactionDAO();
        this.currentUser = currentUser;
    }

    public void addTransaction(Transaction transaction) {
        if (currentUser != null) {
            transaction.setUserId(currentUser.getId());
            transactionDAO.insertTransaction(transaction);
        } else {
            throw new UserException("User not found");
        }
    }

    public void updateTransaction(Transaction transaction) {
        if (currentUser != null && transaction.getUserId() == currentUser.getId()) {
            transactionDAO.updateTransaction(transaction);
        } else {
            throw new UserException("User not found");
        }
    }

    public void deleteTransaction(int transactionId) {
        if (currentUser != null && transactionId == currentUser.getId()) {
            transactionDAO.deleteTransaction(transactionId);
        } else {
            throw new UserException("User not found");
        }
    }

    public List<Transaction> getAllTransactions() {
        if (currentUser != null) {
            return transactionDAO.getAllTransactionsByUserId(currentUser.getId());
        } else {
            System.out.println("User belum login.");
            return null;
        }
    }

    public Transaction getTransactionById(int id) {
        return transactionDAO.getTransactionById(id);
    }
}
