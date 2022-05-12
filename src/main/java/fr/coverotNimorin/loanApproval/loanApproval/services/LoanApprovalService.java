package fr.coverotNimorin.loanApproval.loanApproval.services;

import fr.coverotNimorin.loanApproval.loanApproval.responses.BankAccountResponse;
import fr.coverotNimorin.loanApproval.loanApproval.models.BankAccountRisk;
import fr.coverotNimorin.loanApproval.loanApproval.responses.ApprovalResponse;
import fr.coverotNimorin.loanApproval.loanApproval.models.ApprovalStatus;
import fr.coverotNimorin.loanApproval.loanApproval.services.clients.AccountManagerClientService;
import fr.coverotNimorin.loanApproval.loanApproval.services.clients.ApprovalManagerClientService;
import org.springframework.stereotype.Service;

@Service
public class LoanApprovalService {
    private final AccountManagerClientService accountManagerClient;
    private final ApprovalManagerClientService approvalManagerClient;

    private final int APPROVAL_LIMIT = 10_000;

    public LoanApprovalService(
        AccountManagerClientService accountManagerClient,
        ApprovalManagerClientService approvalManagerClient
    ) {
        this.accountManagerClient = accountManagerClient;
        this.approvalManagerClient = approvalManagerClient;
    }

    public ApprovalResponse handle(String accountId, double amount) {
        BankAccountResponse bankAccount = accountManagerClient.getById(accountId);
        ApprovalResponse response;

        if (amount > APPROVAL_LIMIT)
            response = handleBeyondLimit(bankAccount, amount);
        else
            response = handleBelowLimit(bankAccount, amount);

        double newBalance = bankAccount.getBalance();
        if (response.getStatus() == ApprovalStatus.Accepted) {
            newBalance = bankAccount.getBalance() + amount;
            accountManagerClient.update(accountId, newBalance);
        }

        response.setAccountBalance(newBalance);
        return response;
    }

    private ApprovalResponse handleBelowLimit(BankAccountResponse bankAccount, double amount) {

        if (bankAccount.getRisk() == BankAccountRisk.Low) {
            return new ApprovalResponse(
                approvalManagerClient.create(
                    bankAccount.getId(),
                    amount
                ),
                ApprovalStatus.Accepted,
                bankAccount.getBalance() + amount
            );
        }

        return handleBeyondLimit(bankAccount, amount);
    }

    private ApprovalResponse handleBeyondLimit(BankAccountResponse bankAccount, double amount) {
        return approvalManagerClient.evaluate(bankAccount, amount);
    }
}
