package fr.pangolins.leProPlusPlus.requests.companies;

import fr.pangolins.leProPlusPlus.domain.entities.CompanyType;

public class EditCompanyRequest {
    private String name;
    private CompanyType type;

    public EditCompanyRequest() {
        //
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyType getType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }
}
