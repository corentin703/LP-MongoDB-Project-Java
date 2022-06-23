package fr.pangolins.leProPlusPlus.repository;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.domain.entities.Notice;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends MongoRepository<Company, ObjectId> {
   Optional<Company> findByName(String name);

   @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
   List<Company> findCompaniesForName(String name);

   @Aggregation(pipeline = {
     "{ $match: { '_id': ?0 }, }",
     "{ $unwind: '$notices' }",
     "{ $project: { _id: '$notices._id', title: '$notices.title', content: '$notices.content', mark: '$notices.mark', author: '$notices.author', schemaVersion: '$notices.schemaVersion' } }",
     "{ $match: { '_id': ?1 } }",
   })
   List<Notice> getNoticeById(ObjectId companyId, ObjectId noticesId);

   @Aggregation(pipeline = {
     "{ $match: { '_id': ?0 }, }",
     "{ $unwind: '$notices' }",
     "{ $project: { _id: '$notices._id', title: '$notices.title', content: '$notices.content', mark: '$notices.mark', author: '$notices.author', schemaVersion: '$notices.schemaVersion' } }",
     "{ $match: { 'title': ?1 } }",
   })
   List<Notice> getNoticeByTitle(ObjectId companyId, String title);
}
