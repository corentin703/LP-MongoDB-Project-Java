package fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.accountManager;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.SERVICE_UNAVAILABLE, errorCode = "accountManagerClientService.request.badEntity")
public class AccountManagerBadRequestEntityException extends RuntimeException {
    public AccountManagerBadRequestEntityException() {
        super("Bank account webservice responded with BAD_REQUEST status");
    }
}
