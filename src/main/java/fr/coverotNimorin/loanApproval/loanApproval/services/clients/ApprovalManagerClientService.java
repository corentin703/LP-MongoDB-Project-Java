package fr.coverotNimorin.loanApproval.loanApproval.services.clients;

import fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.accountManager.AccountManagerClientServiceNoUrlException;
import fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.accountManager.AccountManagerWebServiceDownException;
import fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.approvalManager.ApprovalManagerBadRequestEntityException;
import fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.approvalManager.ApprovalManagerClientNoResponseBodyException;
import fr.coverotNimorin.loanApproval.loanApproval.models.Approval;
import fr.coverotNimorin.loanApproval.loanApproval.requests.ApprovalCreateRequest;
import fr.coverotNimorin.loanApproval.loanApproval.requests.ApprovalEvaluateRequest;
import fr.coverotNimorin.loanApproval.loanApproval.responses.ApprovalResponse;
import fr.coverotNimorin.loanApproval.loanApproval.responses.BankAccountResponse;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApprovalManagerClientService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public ApprovalManagerClientService(Environment env) throws AccountManagerClientServiceNoUrlException {
        this.baseUrl = env.getProperty("appManager.url");

        if (this.baseUrl == null || this.baseUrl.isBlank())
            throw new AccountManagerClientServiceNoUrlException();
    }

    public Approval create(String accountId, double amount) {
        try {
            final ResponseEntity<Approval> response =
                    restTemplate.postForEntity(baseUrl + "/approvals", new ApprovalCreateRequest(accountId, amount), Approval.class);

            Approval approval = response.getBody();

            if (approval == null)
                throw new ApprovalManagerClientNoResponseBodyException();

            return approval;
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.BAD_REQUEST)
                throw new ApprovalManagerBadRequestEntityException();

            throw new AccountManagerWebServiceDownException(exception.getStatusCode());
        }
    }

    public ApprovalResponse evaluate(BankAccountResponse account, double amount) {
        try {
            final ResponseEntity<ApprovalResponse> response =
                    restTemplate.postForEntity(baseUrl + "/approvals/eval", new ApprovalEvaluateRequest(account, amount), ApprovalResponse.class);

            ApprovalResponse approvalResponse = response.getBody();

            if (approvalResponse == null)
                throw new ApprovalManagerClientNoResponseBodyException();

            return approvalResponse;
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.BAD_REQUEST)
                throw new ApprovalManagerBadRequestEntityException();

            throw new AccountManagerWebServiceDownException(exception.getStatusCode());
        }
    }
}
