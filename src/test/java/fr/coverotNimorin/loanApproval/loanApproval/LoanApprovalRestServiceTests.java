//package fr.coverotNimorin.loanApproval.loanApproval;
//
//import fr.coverotNimorin.loanApproval.loanApproval.requests.BankAccountUpdateRequest;
//import fr.coverotNimorin.loanApproval.loanApproval.responses.BankAccountResponse;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Stack;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class LoanApprovalRestServiceTests {
//
//    private RestTemplate restTemplate;
//
//    private final String testName = "Test" + LocalDateTime.now();
//    private static final double testBalance = 50.25;
//    private String url;
//
//    @LocalServerPort
//    private int port;
//
//    private final Stack<String> bankAccountsIds = new Stack<>();
//
//    @BeforeEach
//    public void beforeEach() {
//        url = "http://localhost:" + port + "/accounts";
//        restTemplate = new RestTemplate();
//    }
//
//    @Test
//    public void createTest() {
//        ResponseEntity<BankAccountUpdateRequest> response = restTemplate.postForEntity(
//            url,
//            new BankAccountEditRequestEntity(testName, testBalance),
//            BankAccountUpdateRequest.class
//        );
//
//        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
//        BankAccountUpdateRequest bankAccount = response.getBody();
//
//        assertNotNull(bankAccount);
//        assertEquals(testName, bankAccount.getOwnerName());
//        assertEquals(testBalance, bankAccount.getBalance());
//
//        bankAccountsIds.push(bankAccount.getId());
//
//        // Essaie de créer un compte au même nom
//        try {
//            response = restTemplate.postForEntity(
//                url,
//                new BankAccountEditRequestEntity(testName, testBalance),
//                BankAccountUpdateRequest.class
//            );
//            fail();
//        } catch (HttpClientErrorException exception) {
//            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
//        }
//    }
//
//    @Test
//    public void getAllTest() {
//        BankAccountUpdateRequest newBankAccount = restTemplate.postForEntity(
//            url,
//            new BankAccountEditRequestEntity(testName, testBalance),
//            BankAccountUpdateRequest.class
//        ).getBody();
//        assertNotNull(newBankAccount);
//        bankAccountsIds.push(newBankAccount.getId());
//
//        BankAccountUpdateRequest newBankAccountBis = restTemplate.postForEntity(
//            url,
//            new BankAccountEditRequestEntity(testName + "2", testBalance),
//            BankAccountUpdateRequest.class
//        ).getBody();
//        assertNotNull(newBankAccountBis);
//        bankAccountsIds.push(newBankAccountBis.getId());
//
//        ResponseEntity<BankAccountUpdateRequest[]> response = restTemplate.getForEntity(
//            url,
//            BankAccountUpdateRequest[].class
//        );
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        BankAccountUpdateRequest[] bankAccounts = response.getBody();
//        assertNotNull(bankAccounts);
//
//        List<BankAccountUpdateRequest> selectedBankAccounts = Arrays.stream(bankAccounts)
//            .filter(bankAccount ->
//                bankAccount.getId().equals(newBankAccount.getId()) || bankAccount.getId().equals(newBankAccountBis.getId())
//            )
//            .sorted(Comparator.comparing(BankAccountUpdateRequest::getOwnerName))
//            .collect(Collectors.toList());
//
//        assertEquals(selectedBankAccounts.size(), 2);
//        assertEquals(selectedBankAccounts.get(0).getBalance(), newBankAccount.getBalance());
//        assertEquals(selectedBankAccounts.get(0).getOwnerName(), newBankAccount.getOwnerName());
//        assertEquals(selectedBankAccounts.get(1).getBalance(), newBankAccountBis.getBalance());
//        assertEquals(selectedBankAccounts.get(1).getOwnerName(), newBankAccountBis.getOwnerName());
//    }
//
//    @Test
//    public void getByIdTest() {
//        BankAccountUpdateRequest newBankAccount = restTemplate.postForEntity(
//            url,
//            new BankAccountEditRequestEntity(testName, testBalance),
//            BankAccountUpdateRequest.class
//        ).getBody();
//        assertNotNull(newBankAccount);
//        bankAccountsIds.push(newBankAccount.getId());
//
//        ResponseEntity<BankAccountResponse> response = restTemplate.getForEntity(
//        url + "/" + newBankAccount.getId(),
//            BankAccountResponse.class
//        );
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        BankAccountResponse bankAccount = response.getBody();
//        assertNotNull(bankAccount);
//        assertEquals(bankAccount.getOwnerName(), newBankAccount.getOwnerName());
//        assertEquals(bankAccount.getBalance(), newBankAccount.getBalance());
//        assertNotNull(bankAccount.getRisk());
//
//        try {
//            response = restTemplate.getForEntity(
//                url + "/123456789",
//                BankAccountResponse.class
//            );
//            fail();
//        } catch (HttpClientErrorException exception) {
//            assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
//        }
//    }
//
//    @Test
//    public void updateBalanceTest() {
//        BankAccountUpdateRequest newBankAccount = restTemplate.postForEntity(
//            url,
//            new BankAccountEditRequestEntity(testName, testBalance),
//            BankAccountUpdateRequest.class
//        ).getBody();
//        assertNotNull(newBankAccount);
//        bankAccountsIds.push(newBankAccount.getId());
//
//        double newBalance = 75.75;
//        RequestEntity<BankAccountEditRequestEntity> request = RequestEntity
//            .put(url + "/" + newBankAccount.getId())
//            .accept(MediaType.APPLICATION_JSON)
//            .body(new BankAccountEditRequestEntity(newBalance));
//
//        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//
//        BankAccountUpdateRequest bankAccount = restTemplate.getForEntity(
//            url + "/" + newBankAccount.getId(),
//            BankAccountUpdateRequest.class
//        ).getBody();
//        assertNotNull(bankAccount);
//        assertEquals(newBalance, bankAccount.getBalance());
//    }
//
//    @Test
//    public void updateOwnerTest() {
//        BankAccountUpdateRequest newBankAccount = restTemplate.postForEntity(
//            url,
//            new BankAccountEditRequestEntity(testName, testBalance),
//            BankAccountUpdateRequest.class
//        ).getBody();
//        assertNotNull(newBankAccount);
//        bankAccountsIds.push(newBankAccount.getId());
//
//        String newOwnerName = testName + "updateTest";
//        RequestEntity<BankAccountEditRequestEntity> request = RequestEntity
//            .put(url + "/" + newBankAccount.getId())
//            .accept(MediaType.APPLICATION_JSON)
//            .body(new BankAccountEditRequestEntity(newOwnerName));
//
//        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//
//        BankAccountUpdateRequest bankAccount = restTemplate.getForEntity(
//            url + "/" + newBankAccount.getId(),
//            BankAccountUpdateRequest.class
//        ).getBody();
//        assertNotNull(bankAccount);
//        assertEquals(newOwnerName, bankAccount.getOwnerName());
//    }
//
//    @Test
//    public void updateBalanceAndOwnerTest() {
//        BankAccountUpdateRequest newBankAccount = restTemplate.postForEntity(
//            url,
//            new BankAccountEditRequestEntity(testName, testBalance),
//            BankAccountUpdateRequest.class
//        ).getBody();
//        assertNotNull(newBankAccount);
//        bankAccountsIds.push(newBankAccount.getId());
//
//        String newOwnerName = testName + "updateTest";
//        double newBalance = 13.666;
//        RequestEntity<BankAccountEditRequestEntity> request = RequestEntity
//            .put(url + "/" + newBankAccount.getId())
//            .accept(MediaType.APPLICATION_JSON)
//            .body(new BankAccountEditRequestEntity(newOwnerName, newBalance));
//
//        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//
//        BankAccountResponse bankAccount = restTemplate.getForEntity(
//            url + "/" + newBankAccount.getId(),
//            BankAccountResponse.class
//        ).getBody();
//        assertNotNull(bankAccount);
//        assertEquals(newBalance, bankAccount.getBalance());
//        assertEquals(newOwnerName, bankAccount.getOwnerName());
//    }
//
//    @Test
//    public void deleteTest() {
//        BankAccountUpdateRequest newBankAccount = restTemplate.postForEntity(
//            url,
//            new BankAccountEditRequestEntity(testName, testBalance),
//            BankAccountUpdateRequest.class
//        ).getBody();
//        assertNotNull(newBankAccount);
//
//        restTemplate.delete(url + "/" + newBankAccount.getId());
//
//        // Try to get removed one
//        try {
//            ResponseEntity<BankAccountResponse> __ = restTemplate.getForEntity(
//            url + "/" + newBankAccount.getId(),
//                BankAccountResponse.class
//            );
//            fail();
//        } catch (HttpClientErrorException exception) {
//            assertEquals(exception.getStatusCode(), HttpStatus.NOT_FOUND);
//        }
//
//        // Try to remove not existing one
//        try {
//            restTemplate.delete(
//                url + "/" + newBankAccount.getId()
//            );
//            fail();
//        } catch (HttpClientErrorException exception) {
//            assertEquals(exception.getStatusCode(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @AfterEach
//    public void afterEach() {
//
//        // Delete created accounts
//        while (!bankAccountsIds.isEmpty()) {
//            String id = bankAccountsIds.pop();
//            restTemplate.delete(url + "/" + id);
//        }
//    }
//}