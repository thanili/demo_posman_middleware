package org.example.source_api.api;

import org.example.api.entity.Customer;
import org.example.source_api.repository.transaction.CustomerRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.UUID;

/**
 * This controller handles requests related to customer operations.
 * It provides endpoints to retrieve customer information.
 */
@Controller
@RequestMapping("/api/customer")
public class CustomerController {
    private final Logger logger;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(Logger logger, CustomerRepository customerRepository) {
        this.logger = logger;
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves a customer by their unique identifier.
     *
     * @param id The unique identifier of the customer. It must be a valid UUID string.
     * @return ResponseEntity containing the customer if found, or an appropriate error status if not found,
     * if the UUID is invalid, or if an unexpected error occurs.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id){
        try {
            UUID uuid = UUID.fromString(id);
            Optional<Customer> customer = customerRepository.findById(uuid);
            if(customer.isPresent()){
                logger.info("Customer found with ID: {}", id);
                return ResponseEntity.ok(customer.get());
            } else {
                logger.warn("Customer not found with ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IllegalArgumentException e){
            logger.error("Invalid UUID format: {}", id, e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching customer with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Retrieves all customers from the repository.
     *
     * @return ResponseEntity containing an Iterable of all customers.
     */
    @GetMapping("/all")
    public ResponseEntity<Iterable<Customer>> getAllCustomers(){
        logger.info("Fetching all customers");
        return ResponseEntity.ok(customerRepository.findAll());
    }
}
