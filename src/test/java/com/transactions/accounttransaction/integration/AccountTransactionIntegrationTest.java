package com.transactions.accounttransaction.integration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@Sql({"/sql/purge.sql", "/sql/seed.sql"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AccountTransactionIntegrationTest {

    private static final String requestEndpointBase = "http://localhost:8090/account-transaction";

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static String readJSON(String filename) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + filename), "UTF-8");
        } catch (IOException exception) {
            return null;
        }
    }

    private HttpHeaders buildHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    public void shouldReturn200WhenCreateNewClientWithSuccess() {
        String payload = readJSON("request/createClientSuccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase.concat("/client/createClient"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturn400WhenCreateNewClientWithIdAlreadyExist() {
        String payload = readJSON("request/createClientWithIdAlreadyExist.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase.concat("/client/createClient"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(StringUtils.contains(response.getBody(), "This client id: 2 already exist in our database" ));
    }

    @Test
    public void shouldReturn400WhenCreateNewAccountWithIdAlreadyExist() {
        String payload = readJSON("request/createAccountWithAccountIdAlreadyExist.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase.concat("/account/createAccount"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(StringUtils.contains(response.getBody(), "This account id: 3 already exist in our database" ));
    }

    @Test
    public void shouldReturn400WhenCreateNewAccountWithClientNotExistOnDatabase() {
        String payload = readJSON("request/createAccountWithClientNotExist.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase.concat("/account/createAccount"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(StringUtils.contains(response.getBody(), "Client not found with this client id:" ));
    }

    @Test
    public void shouldReturn200WhenPerformNewMoneyTransactionWithSuccess() {
        String payload = readJSON("request/doMoneyTransactionWithSuccess.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase.concat("/transaction/doTransferMoney"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturn400WhenPerformNewMoneyTransactionWithOriginAccountNotFound() {
        String payload = readJSON("request/doMoneyTransactionWithOriginAccountNotFound.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase.concat("/transaction/doTransferMoney"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(StringUtils.contains(response.getBody(), "Origin account not found with this account id: 25" ));
    }

    @Test
    public void shouldReturn400WhenPerformNewMoneyTransactionWithTargetAccountNotFound() {
        String payload = readJSON("request/doMoneyTransactionWithTargetAccountNotFound.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase.concat("/transaction/doTransferMoney"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(StringUtils.contains(response.getBody(), "Target account not found with this account id: 26" ));
    }

    @Test
    public void shouldReturn400WhenPerformNewMoneyTransactionWithOriginAccountHasNoBalance() {
        String payload = readJSON("request/doMoneyTransactionWithOriginAccountHasNoBalance.json");
        HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        ResponseEntity<String> response = testRestTemplate.exchange(requestEndpointBase.concat("/transaction/doTransferMoney"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(StringUtils.contains(response.getBody(), "Origin Client account balance: 0,00 is not enough to transfer this value: 100,50" ));
    }

}
