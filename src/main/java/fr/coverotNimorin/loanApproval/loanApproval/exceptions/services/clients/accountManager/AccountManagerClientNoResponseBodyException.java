package fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.accountManager;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.SERVICE_UNAVAILABLE, errorCode = "accountManagerClientService.noResponseBody")
public class AccountManagerClientNoResponseBodyException extends RuntimeException {
    public AccountManagerClientNoResponseBodyException() {
        super("The check account web service responded with empty body");
    }
}
