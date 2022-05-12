package fr.pangolins.leProPlusPlus.services.entityRepositories;

import fr.pangolins.leProPlusPlus.entities.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {

    List<Company> findAll();

}
