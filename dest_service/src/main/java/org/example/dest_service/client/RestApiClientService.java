package org.example.dest_service.client;

import org.example.api.dto.user.LoginResponseDto;
import org.example.api.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestApiClientService {

    private static final Logger logger = LoggerFactory.getLogger(RestApiClientService.class);

    private final RestTemplate restTemplate;

    @Value("${external.service.api}")
    private String apiUrl;

    @Value("${external.service.endpoint.customer}")
    private String customerEndpoint;
    @Value("${external.service.endpoint.user}")
    private String userEndpoint;
    @Value("${external.service.endpoint.user.secret-key}")
    private String userSecretKey;

    @Autowired
    public RestApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken(String userSecretKey) {
        try {
            LoginResponseDto responseDto = restTemplate
                    .getForObject(apiUrl + userEndpoint + "/login?ASecretKey=" + userSecretKey, LoginResponseDto.class);
            return responseDto.getId();
        } catch (HttpClientErrorException e) {
            // Handle 4xx HTTP errors
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // Handle unauthorized access scenario
                logger.info("Unauthorized access: " + userSecretKey);
                return null;
            } else {
                throw new RuntimeException("Client error when requesting access token: " + e.getMessage(), e);
            }
        } catch (HttpServerErrorException e) {
            // Handle 5xx HTTP errors
            throw new RuntimeException("Server error when requesting access token: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            // Handle errors related to resource access
            throw new RuntimeException("Resource access error when requesting access token: " + e.getMessage(), e);
        } catch (Exception e) {
            // Handle any other errors
            throw new RuntimeException("An error occurred when requesting access token: " + e.getMessage(), e);
        }
    }

    public Customer getCustomer(String id) {
        try {
            String accessToken = getAccessToken(userSecretKey);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Access-Token", accessToken);
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<Customer> response = restTemplate.exchange(
                    apiUrl + customerEndpoint + "/" + id,
                    HttpMethod.GET,
                    requestEntity,
                    Customer.class
            );

            return response.getBody();
            //return restTemplate.getForObject(apiUrl + customerEndpoint + "/" + id, Customer.class);
        } catch (HttpClientErrorException e) {
            // Handle 4xx HTTP errors
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                // Handle resource not found scenario
                logger.info("Customer not found: " + id);
                return null;
            } else {
                throw new RuntimeException("Client error when requesting customer: " + e.getMessage(), e);
            }
        } catch (HttpServerErrorException e) {
            // Handle 5xx HTTP errors
            throw new RuntimeException("Server error when requesting customer: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            // Handle errors related to resource access
            throw new RuntimeException("Resource access error when requesting customer: " + e.getMessage(), e);
        } catch (Exception e) {
            // Handle any other errors
            throw new RuntimeException("An error occurred when requesting customer: " + e.getMessage(), e);
        }
    }
}
