package org.example.source_api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.dto.receipt.ReceiptDto;
import org.example.api.entity.Customer;
import org.example.source_api.entity.transaction.Receipt;
import org.example.source_api.entity.user.ApiUser;
import org.example.source_api.repository.transaction.CustomerRepository;
import org.example.source_api.repository.transaction.ReceiptRepository;
import org.example.source_api.repository.user.ApiUserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ApplicationDataInitializer {
    private final Logger logger;
    private final SampleDataGenerator sampleDataGenerator;
    private final ReceiptRepository receiptRepository;
    private final CustomerRepository customerRepository;
    private final ApiUserRepository apiUserRepository;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public ApplicationDataInitializer(Logger logger,
                                      SampleDataGenerator sampleDataGenerator,
                                      ReceiptRepository receiptRepository,
                                      CustomerRepository customerRepository,
                                      ApiUserRepository apiUserRepository,
                                      ObjectMapper objectMapper
    ) {
        this.logger = logger;
        this.sampleDataGenerator = sampleDataGenerator;
        this.receiptRepository = receiptRepository;
        this.customerRepository = customerRepository;
        this.apiUserRepository = apiUserRepository;
        this.objectMapper = objectMapper;
    }

    @EventListener
    //@Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Optional<ApiUser> apiUser = apiUserRepository.findByIdUsername("apiUser");
        if(apiUser.isEmpty()){
            sampleDataGenerator.createApiUser();
            apiUserRepository.save(sampleDataGenerator.createApiUser());
        }

        List<Customer> customers = sampleDataGenerator.generateCustomers(20);
        customerRepository.saveAll(customers);

        List<ReceiptDto> receiptDtos = sampleDataGenerator.generateReceipts(100);
        receiptDtos.forEach(receiptDto -> {
            try {
                Receipt receipt = new Receipt();
                receipt.setReceiptJson(objectMapper.writeValueAsString(receiptDto));
                receiptRepository.save(receipt);
            } catch (JsonProcessingException e) {
                //throw new RuntimeException(e);
                logger.error("Error during receiptDto deserialization: "+ e.getMessage());
            }
        });


    }

    @EventListener
    //@Transactional
    public void onApplicationShutdown(ContextClosedEvent event) {
        receiptRepository.deleteAll();
        customerRepository.deleteAll();
    }
}
