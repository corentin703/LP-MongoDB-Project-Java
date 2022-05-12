package fr.pangolins.leProPlusPlus.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("companies")
public class Company {
    @Id
    private String id;
    private String name;
    private List<Notice> notices;
    private CompanyType type;

    public Company() {
        //
    }

    public Company(String name, List<Notice> notices, CompanyType type) {
        this.name = name;
        this.notices = notices;
        this.type = type;
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

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    public CompanyType getType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }
}
