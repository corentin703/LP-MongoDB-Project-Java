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

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<CompanyResponse>> getAll() {
        List<Company> companies = companyRepository.findAll();
        companies.forEach(companySchemaVersioning::run);

        return new ResponseEntity<>(
            companies.stream().map(CompanyResponse::new).collect(Collectors.toList()),
            HttpStatus.OK
        );
    }

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
