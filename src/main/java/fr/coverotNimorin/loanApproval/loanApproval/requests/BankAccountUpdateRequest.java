package fr.coverotNimorin.loanApproval.loanApproval.requests;

public class BankAccountUpdateRequest {
    private double balance;

    public BankAccountUpdateRequest(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
