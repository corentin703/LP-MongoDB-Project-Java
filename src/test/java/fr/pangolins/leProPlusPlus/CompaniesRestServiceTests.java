package fr.pangolins.leProPlusPlus;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Stack;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompaniesRestServiceTests {
    private RestTemplate restTemplate;
    private String url;

    @LocalServerPort
    private int port;

    private final Stack<String> bankAccountsIds = new Stack<>();

    @BeforeEach
    public void beforeEach() {
        url = "http://localhost:" + port + "/companies";
        restTemplate = new RestTemplate();
    }
}
