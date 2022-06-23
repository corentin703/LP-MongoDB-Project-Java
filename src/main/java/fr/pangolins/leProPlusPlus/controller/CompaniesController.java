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
     * Récupère l'ensemble des entreprises
     *
     * @return ResponseEntity contenant la liste de toutes les entreprises
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
     * Récupère une entreprise suivant un id donné
     *
     * @param id Id de l'entreprise qu'on souhaite récupérer
     * @return ResponseEntity contenant l'entreprise associée
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
     * Récupère une entreprise suivant un nom donné
     *
     * @param name le nom de la company que l'on souhaite récupérer
     * @return une ResponseEntity contenant l'entreprise correspondante au nom fourni
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
     * Recherche d'entreprises correspondant au nom partiel ou complet passé en paramètres.
     *
     * La recherche est insensible à la casse.
     * @param name Nom partiel ou complet des entreprise(s) recherchée(s)
     * @return ResponseEntity&lt;List&lt;CompanyResponse&gt;&gt; une liste d'entreprises correspondant à la chaîne passée en paramètres
     */
    @GetMapping("search/{name}")
    public ResponseEntity<List<CompanyResponse>> searchByName(@PathVariable String name) {
        List<Company> companies;

        try {
            companies = companyRepository.findCompaniesByName(name);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(name);
        }

        return new ResponseEntity<>(
                companies.stream().map(CompanyResponse::new).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    /**
     * Crée une entreprise
     *
     * @param request Corps de requête comportant le nom et le type de l'entreprise qu'on souhaite créer
     * @return une ResponseEntity avec l'entreprise créée
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
     * Mets à jour une entreprise
     *
     * @param id Identifiant de l'entreprise
     * @param request Contient le nouveau nom et type de la company à mettre à jour
     * @return ResponseEntity avec le code de retour uniquement
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
     * Supprime une entreprise
     *
     * @param id Identifiant de l'entreprise à supprimer
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
