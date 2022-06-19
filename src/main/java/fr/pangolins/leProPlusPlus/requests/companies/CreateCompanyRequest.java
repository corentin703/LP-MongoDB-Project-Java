package fr.pangolins.leProPlusPlus.requests.companies;

import fr.pangolins.leProPlusPlus.domain.entities.CompanyType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCompanyRequest {
    @NotBlank(message = "company.name.required")
    private String name;

    @NotNull(message = "company.type.required")
    private CompanyType type;

    public CreateCompanyRequest() {
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
