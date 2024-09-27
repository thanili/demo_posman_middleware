package org.example.source_api.service;

import org.example.api.dto.ResponseDto;
import org.example.api.dto.receipt.ReceiptDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Service for handling the transfer of receipt data to an external service.
 */
@Service
public class ReceiptDataTransferService {
    private final Logger logger;
    private final RestTemplate restTemplate;

    @Value("${external.service.api}")
    private String baseUrl;
    @Value("${external.service.user}")
    private String username;
    @Value("${external.service.password}")
    private String password;

    @Autowired
    public ReceiptDataTransferService(Logger logger, RestTemplate restTemplate) {
        this.logger = logger;
        this.restTemplate = restTemplate;
    }

    /**
     * Sends receipt data to an external receipt import service.
     *
     * @param receiptDto The data transfer object containing receipt details.
     * @return ResponseDto containing the result of the receipt import request.
     */
    public ResponseDto postReceiptData(ReceiptDto receiptDto) {
        String url = buildReceiptImportUrl();

        HttpHeaders headers = createHeaders(username, password);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReceiptDto> requestEntity = new HttpEntity<>(receiptDto, headers);

        return executeReceiptImportRequest(url, requestEntity);
    }

    /**
     * Builds the URL for importing receipts by appending the received base URL
     * with the path "/import/receipt".
     *
     * @return Constructed URL as a String for the receipt import endpoint.
     */
    private String buildReceiptImportUrl() {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/api/import/receipt")
                .toUriString();
    }

    /**
     * Creates HttpHeaders with Basic Authentication using the provided username and password.
     *
     * @param username The username for Basic Authentication.
     * @param password The password for Basic Authentication.
     * @return HttpHeaders object populated with Basic Authentication credentials.
     */
    private HttpHeaders createHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        return headers;
    }

    /**
     * Executes the receipt import request to the specified URL with the given request entity.
     *
     * @param url The target URL of the external receipt import service.
     * @param requestEntity The HTTP entity containing the receipt data to be imported.
     * @return ResponseDto containing the result of the receipt import request.
     */
    private ResponseDto executeReceiptImportRequest(String url, HttpEntity<ReceiptDto> requestEntity) {
        try {
            ResponseEntity<ResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseDto.class);
            if (response.getBody() != null) {
                return response.getBody();
            } else {
                logger.error("Empty response body received");
                return new ResponseDto(1, "Empty response body received");
            }
        } catch (Exception e) {
            logger.error("Error during receipt import: " + e.getMessage());
            return new ResponseDto(1, "Error during receipt import: " + e.getMessage());
        }
    }
}
