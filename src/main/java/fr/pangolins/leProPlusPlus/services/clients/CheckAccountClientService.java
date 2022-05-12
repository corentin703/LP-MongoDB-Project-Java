package fr.pangolins.leProPlusPlus.services.clients;

import fr.pangolins.leProPlusPlus.entities.BankAccount;
import fr.pangolins.leProPlusPlus.exceptions.services.clients.CheckAccountClientHttpException;
import fr.pangolins.leProPlusPlus.exceptions.services.clients.CheckAccountClientNoResponseBodyException;
import fr.pangolins.leProPlusPlus.models.BankAccountGetRiskResponse;
import fr.pangolins.leProPlusPlus.models.BankAccountRisk;
import fr.pangolins.leProPlusPlus.exceptions.services.clients.CheckAccountClientServiceNoUrlException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CheckAccountClientService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;

    public CheckAccountClientService(Environment env) throws CheckAccountClientServiceNoUrlException {
        this.baseUrl = env.getProperty("checkAccount.url");

        if (this.baseUrl == null || this.baseUrl.isBlank())
            throw new CheckAccountClientServiceNoUrlException();
    }

    public BankAccountRisk getBankAccountRisk(BankAccount bankAccount) {
        try {
            ResponseEntity<BankAccountGetRiskResponse> response = restTemplate.getForEntity(
                    baseUrl + "/checkAccount/risk?id=" + bankAccount.getId() + "&ownerName=" + bankAccount.getOwnerName() + "&balance=" + bankAccount.getBalance(),
                    BankAccountGetRiskResponse.class
            );

            if (response.getStatusCode() != HttpStatus.OK)
                throw new CheckAccountClientHttpException(response.getStatusCode());

            BankAccountGetRiskResponse responseBody = response.getBody();

            if (responseBody == null)
                throw new CheckAccountClientNoResponseBodyException();

            return responseBody.getRisk();
        } catch (HttpClientErrorException restClientException) {
            throw new CheckAccountClientHttpException(restClientException.getStatusCode());
        }
    }
}
