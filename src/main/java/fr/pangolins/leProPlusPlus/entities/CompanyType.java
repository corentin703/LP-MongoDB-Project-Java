package fr.pangolins.leProPlusPlus.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("companyTypes")
public class CompanyType {
    @Id
    private String id;

    private String name;

    public CompanyType() {
        //
    }

    public CompanyType(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
