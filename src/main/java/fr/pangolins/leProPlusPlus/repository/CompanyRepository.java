package fr.pangolins.leProPlusPlus.repository;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends MongoRepository<Company, ObjectId> {
   Optional<Company> findByName(String name);
}
