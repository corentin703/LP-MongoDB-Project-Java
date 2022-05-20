package fr.pangolins.leProPlusPlus.repository;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, ObjectId> {



}