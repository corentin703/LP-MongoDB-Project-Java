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

   /**
    * Cherche une entreprise avec un nom donné
    *
    * @param name Nom de l'entreprise recherchée
    * @return Entreprise trouvée (= null si introuvable)
    */
   @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
   List<Company> findCompaniesByName(String name);

   /**
    * Récupère un avis avec un _id donné
    *
    * @param companyId Id de l'entreprise
    * @param noticesId Id de l'avis
    * @return Résultat de l'agrégation
    */
   @Aggregation(pipeline = {
     "{ $match: { '_id': ?0 }, }",
     "{ $unwind: '$notices' }",
     "{ $project: { _id: '$notices._id', title: '$notices.title', content: '$notices.content', mark: '$notices.mark', author: '$notices.author', schemaVersion: '$notices.schemaVersion' } }",
     "{ $match: { '_id': ?1 } }",
   })
   List<Notice> getNoticeById(ObjectId companyId, ObjectId noticesId);

   /**
    * Récupère un avis avec un titre donné
    *
    * @param companyId Id de l'entreprise
    * @param title Titre à rechercher
    * @return Résultat de l'agrégation
    */
   @Aggregation(pipeline = {
     "{ $match: { '_id': ?0 }, }",
     "{ $unwind: '$notices' }",
     "{ $project: { _id: '$notices._id', title: '$notices.title', content: '$notices.content', mark: '$notices.mark', author: '$notices.author', schemaVersion: '$notices.schemaVersion' } }",
     "{ $match: { 'title': ?1 } }",
   })
   List<Notice> getNoticeByTitle(ObjectId companyId, String title);
}
