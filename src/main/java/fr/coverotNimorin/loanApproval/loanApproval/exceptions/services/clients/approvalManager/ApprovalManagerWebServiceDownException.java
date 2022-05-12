package fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.approvalManager;

import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.INTERNAL_SERVER_ERROR, errorCode = "approvalManagerClientService.httpError")
public class ApprovalManagerWebServiceDownException extends RuntimeException {
    @ExposeAsArg(0)
    private final HttpStatus statusCode;

    public ApprovalManagerWebServiceDownException(HttpStatus statusCode) {
        super("Approval manager web service is down. Please contact an administrator. Responded with status code " + statusCode);
        this.statusCode = statusCode;
    }
}

