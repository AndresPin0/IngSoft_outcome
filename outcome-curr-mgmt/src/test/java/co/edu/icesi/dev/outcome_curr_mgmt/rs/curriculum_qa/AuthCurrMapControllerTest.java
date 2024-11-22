package co.edu.icesi.dev.outcome_curr_mgmt.testing.system.rs.curriculum_qa;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.curriculum_qa.AssmtGenPlanOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.rs.curriculum_qa.AuthAssessmentGenPlanControllerImpl;
import co.edu.icesi.dev.outcome_curr_mgmt.testing.system.rs.util.BaseSmokeIT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {AuthAssessmentGenPlanControllerImpl.class})
@RequiredArgsConstructor
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssessmentGenPlanControllerSmokeIT extends BaseSmokeIT {

    @Value("${test.server.url}")
    private String server;

    private static String testUserJWTToken;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeAll
    void init() {
        testUserJWTToken = getTestUserJWTToken("OutCurrTestUser", "123456", server);
    }

    @Test
    void getAssessmentGenPlanList() {
        HttpEntity<String> jwtEntity = new HttpEntity<>(getRequestHeaders());

        String url = server + "/outcurrapi/v1/assessment/genplan?facultyId=1&acadProgId=1";
        ResponseEntity<List<AssmtGenPlanOutDTO>> response = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                jwtEntity,
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAssessmentGenPlanById() {
        HttpEntity<String> jwtEntity = new HttpEntity<>(getRequestHeaders());

        String url = server + "/outcurrapi/v1/assessment/genplan/1?facultyId=1&acadProgId=1";
        ResponseEntity<AssmtGenPlanOutDTO> response = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                jwtEntity,
                new ParameterizedTypeReference<>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    private HttpHeaders getRequestHeaders() {
        HttpHeaders headers = getHeaders();
        headers.set("Authorization", "Bearer " + testUserJWTToken);
        return headers;
    }
}
