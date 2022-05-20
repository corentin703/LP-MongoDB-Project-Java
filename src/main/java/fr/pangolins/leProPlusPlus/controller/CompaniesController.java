package fr.pangolins.leProPlusPlus.controller;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.domain.entities.CompanyType;
import fr.pangolins.leProPlusPlus.repository.CompanyRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/companies")
public class CompaniesController {


    private final CompanyRepository companyRepository;

    public CompaniesController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Company>> getAll() {

        List<Company> companies = companyRepository.findAll();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
    //


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        return new ResponseEntity<>(companyRepository.findById(new ObjectId(id)), HttpStatus.OK);
    }
//
    @PostMapping
    public ResponseEntity<?> create(@RequestParam("name") String name){
        Company newCompany = new Company();
        newCompany.setName(name);
        return new ResponseEntity<>(companyRepository.insert(newCompany), HttpStatus.CREATED);
    }
//
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @RequestParam("name") String name,
            @RequestParam("companyType") CompanyType companyType) {


        Optional<Company> company = companyRepository.findById(new ObjectId(id));
        if (company.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Company updatedCompany = company.get();

        updatedCompany.setName(name);
        updatedCompany.setType(companyType);

        companyRepository.save(updatedCompany);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){

        Optional<Company> company = companyRepository.findById(new ObjectId(id));
        if (company.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        companyRepository.delete(company.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
