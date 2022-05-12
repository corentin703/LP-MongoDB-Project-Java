package fr.pangolins.leProPlusPlus.exceptions.services.clients;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.INTERNAL_SERVER_ERROR, errorCode = "checkAccountClientService.httpError")
public class CheckAccountClientHttpException extends RuntimeException {
    @ExposeAsArg(0)
    private final HttpStatus statusCode;

    public CheckAccountClientHttpException(HttpStatus statusCode) {
        super("Error with the check account web service : responded with status " + statusCode);
        this.statusCode = statusCode;
    }
}

