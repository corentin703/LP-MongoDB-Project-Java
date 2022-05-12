package fr.coverotNimorin.loanApproval.loanApproval.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoanApprovalAskRequest {
    @NotBlank(message = "loanApproval.accountId.required")
    private String accountId;

    @NotNull(message = "loanApproval.amount.required")
    private Double amount;

    public LoanApprovalAskRequest() {
        //
    }

    public LoanApprovalAskRequest(String accountId, Double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
