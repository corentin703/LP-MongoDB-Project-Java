package fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.accountManager;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "accountManagerClientService.notFound.id")
public class AccountManagerNotFoundByIdException extends RuntimeException {
    @ExposeAsArg(0)
    private final String id;

    public AccountManagerNotFoundByIdException(String id) {
        super("Account not found with id " + id + ".");
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
