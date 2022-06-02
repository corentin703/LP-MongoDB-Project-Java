package fr.pangolins.leProPlusPlus.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class CompanyType {
    private String name;

    public CompanyType() {
        //
    }

    public CompanyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
