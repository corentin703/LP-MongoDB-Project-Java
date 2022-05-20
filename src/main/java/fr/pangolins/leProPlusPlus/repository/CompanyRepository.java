package fr.pangolins.leProPlusPlus.repository;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {

    List<Company> findAll();

}
