package fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.accountManager;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.INTERNAL_SERVER_ERROR, errorCode = "accountManagerClientService.httpError")
public class AccountManagerWebServiceDownException extends RuntimeException {
    @ExposeAsArg(0)
    private final HttpStatus statusCode;

    public AccountManagerWebServiceDownException(HttpStatus statusCode) {
        super("Account manager web service is down. Please contact an administrator. Responded with status code " + statusCode);
        this.statusCode = statusCode;
    }
}

