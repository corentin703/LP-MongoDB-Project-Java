package fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.approvalManager;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.SERVICE_UNAVAILABLE, errorCode = "approvalManagerClientService.noResponseBody")
public class ApprovalManagerClientNoResponseBodyException extends RuntimeException {
    public ApprovalManagerClientNoResponseBodyException() {
        super("Approval web service responded with empty body");
    }
}
