package fr.pangolins.leProPlusPlus.responses;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.domain.entities.CompanyType;
import fr.pangolins.leProPlusPlus.domain.entities.Notice;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyResponse {
    private String id;
    private String name;
    private List<NoticeResponse> notices;
    private CompanyType type;

    public CompanyResponse() {
        //
    }

    public CompanyResponse(Company company) {
        id = company.getId().toHexString();
        name = company.getName();

        if (company.getNotices() != null)
            notices = company.getNotices().stream().map(NoticeResponse::new).collect(Collectors.toList());

        type = company.getType();
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

    public List<NoticeResponse> getNotices() {
        return notices;
    }

    public void setNotices(List<NoticeResponse> notices) {
        this.notices = notices;
    }

    public CompanyType getType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }
}
