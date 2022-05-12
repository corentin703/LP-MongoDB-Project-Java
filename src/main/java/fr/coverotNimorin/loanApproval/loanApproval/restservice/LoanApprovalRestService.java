package fr.coverotNimorin.loanApproval.loanApproval.restservice;

import fr.coverotNimorin.loanApproval.loanApproval.requests.LoanApprovalAskRequest;
import fr.coverotNimorin.loanApproval.loanApproval.responses.ApprovalResponse;
import fr.coverotNimorin.loanApproval.loanApproval.services.LoanApprovalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Validated
@Controller
@RequestMapping("/loan")
public class LoanApprovalRestService {
    private final LoanApprovalService loanApprovalService;

    public LoanApprovalRestService(LoanApprovalService loanApprovalService) {
        this.loanApprovalService = loanApprovalService;
    }

    @RequestMapping(path = "ask", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<ApprovalResponse> ask(@RequestBody @Valid LoanApprovalAskRequest request) {
        ApprovalResponse response = loanApprovalService.handle(request.getAccountId(), request.getAmount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
