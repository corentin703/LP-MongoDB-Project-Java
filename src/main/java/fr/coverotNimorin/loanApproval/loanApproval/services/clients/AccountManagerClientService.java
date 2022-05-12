package fr.coverotNimorin.loanApproval.loanApproval.services.clients;

import fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.accountManager.*;
import fr.coverotNimorin.loanApproval.loanApproval.requests.BankAccountUpdateRequest;
import fr.coverotNimorin.loanApproval.loanApproval.responses.BankAccountResponse;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountManagerClientService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public AccountManagerClientService(Environment env) throws AccountManagerClientServiceNoUrlException {
        this.baseUrl = env.getProperty("accManager.url");

        if (this.baseUrl == null || this.baseUrl.isBlank())
            throw new AccountManagerClientServiceNoUrlException();
    }

    public BankAccountResponse getById(String accountId) {
        try {
            final ResponseEntity<BankAccountResponse> response =
                    restTemplate.getForEntity(baseUrl + "/accounts/" + accountId, BankAccountResponse.class);

            BankAccountResponse bankAccountResponse = response.getBody();

            if (bankAccountResponse == null)
                throw new AccountManagerClientNoResponseBodyException();

            return bankAccountResponse;
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new AccountManagerNotFoundByIdException(accountId);

            throw new AccountManagerWebServiceDownException(exception.getStatusCode());
        }
    }

    public void update(String accountId, double newAmount) {
        try {
            restTemplate.put(baseUrl + "/accounts/" + accountId, new BankAccountUpdateRequest(newAmount));
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new AccountManagerNotFoundByIdException(accountId);

            if (exception.getStatusCode() == HttpStatus.BAD_REQUEST)
                throw new AccountManagerBadRequestEntityException();

            throw new AccountManagerWebServiceDownException(exception.getStatusCode());
        }
    }
}
