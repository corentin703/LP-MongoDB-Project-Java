package fr.coverotNimorin.loanApproval.loanApproval.requests;

import fr.coverotNimorin.loanApproval.loanApproval.responses.BankAccountResponse;

public class ApprovalEvaluateRequest {
    private BankAccountResponse account;
    private Double amount;

    public ApprovalEvaluateRequest() {
        //
    }

    public ApprovalEvaluateRequest(BankAccountResponse account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    public BankAccountResponse getAccount() {
        return account;
    }
    public void setAccount(BankAccountResponse account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
