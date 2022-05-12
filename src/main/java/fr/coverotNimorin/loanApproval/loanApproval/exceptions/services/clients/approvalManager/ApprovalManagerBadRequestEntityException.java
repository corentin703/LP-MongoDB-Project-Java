package fr.coverotNimorin.loanApproval.loanApproval.exceptions.services.clients.approvalManager;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.http.HttpStatus;

@ExceptionMapping(statusCode = HttpStatus.SERVICE_UNAVAILABLE, errorCode = "approvalManagerClientService.request.badEntity")
public class ApprovalManagerBadRequestEntityException extends RuntimeException {
    public ApprovalManagerBadRequestEntityException() {
        super("Approval webservice responded with BAD REQUEST status");
    }
}
