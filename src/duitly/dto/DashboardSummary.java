/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package duitly.dto;

import java.math.BigDecimal;

/**
 *
 * @author rahadityaputra
 */
public class DashboardSummary {
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal balance;

    public DashboardSummary(BigDecimal income, BigDecimal expense) {
        this.income = income;
        this.expense = expense;
        this.balance = income.subtract(expense);
    }

    public BigDecimal getIncome() { return income; }
    public BigDecimal getExpense() { return expense; }
    public BigDecimal getBalance() { return balance; }
}
