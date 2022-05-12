package fr.pangolins.leProPlusPlus.restservice;

import fr.pangolins.leProPlusPlus.entities.BankAccount;
import fr.pangolins.leProPlusPlus.models.BankAccountResponse;
import fr.pangolins.leProPlusPlus.exceptions.bankAccount.BankAccountRequestNoBodyException;
import fr.pangolins.leProPlusPlus.requestEntities.BankAccountCreateRequestEntity;
import fr.pangolins.leProPlusPlus.requestEntities.BankAccountEditRequestEntity;
import fr.pangolins.leProPlusPlus.services.clients.CheckAccountClientService;
import fr.pangolins.leProPlusPlus.services.entityRepositories.BankAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotBlank;

import java.util.List;

@Validated
@Controller
@RequestMapping("/accounts")
public class BankAccountRestService {
    private final BankAccountRepository bankAccountRepository;
    private final CheckAccountClientService checkAccountClientService;

    public BankAccountRestService(
        BankAccountRepository bankAccountRepository,
        CheckAccountClientService checkAccountClientService
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.checkAccountClientService = checkAccountClientService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<BankAccount>> getAll() {
        return new ResponseEntity<>(bankAccountRepository.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public @ResponseBody ResponseEntity<BankAccountResponse> getById(
        @PathVariable(name = "id")
        @NotBlank(message = "restService.bankAccount.getById.id.notBlank") String id
    ) {
        BankAccount bankAccount = bankAccountRepository.getById(id);
        BankAccountResponse bankAccountResponse = new BankAccountResponse(
            bankAccount,
            checkAccountClientService.getBankAccountRisk(bankAccount)
        );

        return new ResponseEntity<>(bankAccountResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/ownerName/{name}")
    public @ResponseBody ResponseEntity<BankAccountResponse> getByOwnerName(
        @PathVariable(name = "name")
        @NotBlank(message = "restService.bankAccount.getByOwnerName.name.notBlank") String name
    ) {
        BankAccount bankAccount = bankAccountRepository.getByOwnerName(name);
        BankAccountResponse bankAccountResponse = new BankAccountResponse(
                bankAccount,
                checkAccountClientService.getBankAccountRisk(bankAccount)
        );

        return new ResponseEntity<>(bankAccountResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<BankAccount> create(@RequestBody @Valid BankAccountCreateRequestEntity request) {
        if (request == null)
            throw new BankAccountRequestNoBodyException();

        BankAccount bankAccount = bankAccountRepository.create(request.getOwnerName(), request.getBalance());
        return new ResponseEntity<>(bankAccount, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public @ResponseBody ResponseEntity<BankAccount> update(
            @PathVariable(name = "id") @NotBlank(message = "restService.bankAccount.update.id.notBlank") String id,
            @RequestBody @Valid BankAccountEditRequestEntity request
    ) {
        if (request == null)
            throw new BankAccountRequestNoBodyException();

        BankAccount __ = bankAccountRepository.update(id, request.getBalance(), request.getOwnerName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public @ResponseBody ResponseEntity<BankAccount> delete(
            @PathVariable(name = "id") @NotBlank(message = "restService.bankAccount.delete.id.notBlank") String id
    ) {
        bankAccountRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
