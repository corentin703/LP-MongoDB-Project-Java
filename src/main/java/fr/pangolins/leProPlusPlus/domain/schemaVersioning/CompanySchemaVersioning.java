package fr.pangolins.leProPlusPlus.domain.schemaVersioning;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanySchemaVersioning implements EntitySchemaVersioning<Company> {
    private final CompanyRepository companyRepository;
    private final int LAST_SCHEMA_VERSION = 1;

    public CompanySchemaVersioning(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void run(Company company) {
        if (company.getSchemaVersion() >= LAST_SCHEMA_VERSION) {
            return;
        }

        companyRepository.deleteById(company.getId());
        company.setSchemaVersion(LAST_SCHEMA_VERSION);
        companyRepository.insert(company);
    }
}
