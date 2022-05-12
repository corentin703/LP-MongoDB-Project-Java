package fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.approvalManager;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.BAD_REQUEST, errorCode = "approvalManagerClientService.noUrl")
public class ApprovalManagerClientServiceNoUrlException extends RuntimeException {
    public ApprovalManagerClientServiceNoUrlException() {
        super("No url has been provided for consuming the Approval service");
    }
}
