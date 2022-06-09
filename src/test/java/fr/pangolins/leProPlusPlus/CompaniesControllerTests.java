package fr.pangolins.leProPlusPlus;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.repository.CompanyRepository;
import fr.pangolins.leProPlusPlus.responses.CompanyResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompaniesControllerTests {
    private String url;
    private RestTemplate restTemplate;

    @Autowired
    private CompanyRepository companyRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void beforeEach() {
        url = "http://localhost:" + port + "/companies";
        restTemplate = new RestTemplate();
    }
    @Test
    public void getAllIsOK() throws Exception {
         ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET,null , List.class);
         System.out.println(response.getStatusCode());
         assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void createIsOk() throws Exception {
        //Given
        String requestParams = "?name=company1";

        //When
        ResponseEntity response = restTemplate.exchange(url + requestParams, HttpMethod.POST,null , CompanyResponse.class);

        //Then
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(((CompanyResponse) response.getBody()).getName(), "company1");
    }
}
