package fr.coverotNimorin.loanApproval.loanApproval.responses;

import fr.coverotNimorin.loanApproval.loanApproval.models.Approval;
import fr.coverotNimorin.loanApproval.loanApproval.models.ApprovalStatus;

public class ApprovalResponse {
    private Approval approval;
    private ApprovalStatus status;

    private double accountBalance;

    public ApprovalResponse() {
        //
    }

    public ApprovalResponse(Approval approval, ApprovalStatus status, double accountBalance) {
        this.approval = approval;
        this.status = status;
        this.accountBalance = accountBalance;
    }

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
