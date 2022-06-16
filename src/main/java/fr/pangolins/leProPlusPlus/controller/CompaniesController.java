package fr.pangolins.leProPlusPlus.controller;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.domain.exception.entities.EntityNotFoundException;
import fr.pangolins.leProPlusPlus.domain.exception.entities.InvalidObjectIdException;
import fr.pangolins.leProPlusPlus.domain.schemaVersioning.CompanySchemaVersioning;
import fr.pangolins.leProPlusPlus.repository.CompanyRepository;
import fr.pangolins.leProPlusPlus.requests.companies.CreateCompanyRequest;
import fr.pangolins.leProPlusPlus.requests.companies.EditCompanyRequest;
import fr.pangolins.leProPlusPlus.responses.CompanyResponse;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Validated
@RestController
@RequestMapping("/companies")
public class CompaniesController {
    private final CompanyRepository companyRepository;
    private final CompanySchemaVersioning companySchemaVersioning;

    public CompaniesController(CompanyRepository companyRepository, CompanySchemaVersioning companySchemaVersioning) {
        this.companyRepository = companyRepository;
        this.companySchemaVersioning = companySchemaVersioning;
    }

    /**
     * GetAll permet de recupérer l'ensemble des companies via des  CompanyResponses
     * @return une ResponseEntity avec la liste des CompanyResponse
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<CompanyResponse>> getAll() {
        List<Company> companies = companyRepository.findAll();
        companies.forEach(companySchemaVersioning::run);

        return new ResponseEntity<>(
            companies.stream().map(CompanyResponse::new).collect(Collectors.toList()),
            HttpStatus.OK
        );
    }

    /**
     * GetById permet de recupérer une comapny via une companyResponse suivant un id donné
     * @return une ResponseEntity avec la CompanyResponse associée
     * @param id l'id de la comany qu'on souhaite récupérer
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable String id) {
        Optional<Company> company;

        try {
            company = companyRepository.findById(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(id);
        }

        if (company.isEmpty())
            throw new EntityNotFoundException(id);

        companySchemaVersioning.run(company.get());

        return new ResponseEntity<>(
            new CompanyResponse(company.get()),
            HttpStatus.OK
        );
    }

    /**
     * GetByName permet de recupérer une company via une companyResponse suivant un nom donné
     * @return une ResponseEntity avec la CompanyResponse correspondante au nom fourni
     * @param name le nom de la comany qu'on souhaite récupérer
     */
    @GetMapping("name/{name}")
    public ResponseEntity<CompanyResponse> getByName(@PathVariable String name) {
        Optional<Company> company;

        try {
            company = companyRepository.findByName(name);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(name);
        }

        if (company.isEmpty())
            throw new EntityNotFoundException(name);

        companySchemaVersioning.run(company.get());

        return new ResponseEntity<>(
                new CompanyResponse(company.get()),
                HttpStatus.OK
        );
    }

    /**
     * searchByName permet de rechercher une liste de compagnies correspondant en partie au paramètre
     *
     * @param name
     * @return ResponseEntity<List<CompanyResponse>>
     */
    @GetMapping("search/{name}")
    public ResponseEntity<List<CompanyResponse>> searchByName(@PathVariable String name) {
        List<Company> companies;

        try {
            companies = companyRepository.findCompaniesForName(name);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(name);
        }

        return new ResponseEntity<>(
                companies.stream().map(CompanyResponse::new).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    /**
     * Create permet de créer une company suivant une company request donnée
     * @return une ResponseEntity avec la CompanyResponse créée
     * @param request la request comprenant le nom et le type de la company qu'on souhaite créer
     */
    @PostMapping
    public ResponseEntity<CompanyResponse> create(@RequestBody CreateCompanyRequest request) {
        Company newCompany = new Company();
        newCompany.setName(request.getName());
        newCompany.setType(request.getType());

        newCompany = companyRepository.insert(newCompany);

        return new ResponseEntity<>(
                new CompanyResponse(newCompany),
                HttpStatus.CREATED
        );
    }

    /**
     * Update permet de mettre à jour une company
     * @param id identifiant de la company
     * @param request contient le nouveau nom et type de la company à mettre à jour
     * @return une ResponseEntity avec le code de retour uniquement
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(
        @PathVariable String id,
        @RequestBody EditCompanyRequest request
    ) {
        Optional<Company> company;

        try {
            company = companyRepository.findById(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(id);
        }

        if (company.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        Company updatedCompany = company.get();

        if (request.getName() != null) {
            updatedCompany.setName(request.getName());
        }

        if (request.getType() != null) {
            updatedCompany.setType(request.getType());
        }

        companySchemaVersioning.run(updatedCompany);
        companyRepository.save(updatedCompany);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete permet de supprimer une company
     * @param id identifiant de la company à supprimer
     * @return une ResponseEntity avec le code de retour uniquement
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Optional<Company> company;

        try {
            company = companyRepository.findById(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(id);
        }

        if (company.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        companyRepository.delete(company.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
