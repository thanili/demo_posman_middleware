package org.example.dest_service.repository.webshop;

import org.example.dest_service.DestServiceApplication;
import org.example.dest_service.configuration.datasource.PosDataSourceConfiguration;
import org.example.dest_service.configuration.datasource.WebshopDataSourceConfiguration;
import org.example.dest_service.entity.common.CustomerLocal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DestServiceApplication.class, PosDataSourceConfiguration.class, WebshopDataSourceConfiguration.class})
public class CustomerWebshopRepositoryTest {
    @Autowired
    CustomerWebshopRepository customerWebshopRepository;

    private CustomerLocal createCustomer() {
        CustomerLocal customer = new CustomerLocal();
        customer.setCode("1234");
        customer.setCountryCode("FRA");
        customer.setCompany("MSPOS");
        customer.setZipCode("10022");
        customer.setLocation("Paris");
        customer.setStreet("Lombard Street");
        customer.setEMail("ailiop@test.com");
        customer.setDateOfBirth(LocalDate.of(1964,2,2));
        customer.setSalutation("Mr");
        customer.setFirstName("John");
        customer.setLastName("Doe");

        return customer;
    }

    @Test
    public void test_CustomerPosRepo_Save_FindById_Delete() {
        CustomerLocal customer = createCustomer();

        // Save the document
        customerWebshopRepository.save(customer);
        // Try to find the document by its ID
        Optional<CustomerLocal> foundCustomer = customerWebshopRepository.findById(customer.getCode());

        // Check if the document is present
        assertThat(foundCustomer).isPresent();

        // Validate the found document
        assertThat(foundCustomer.get().getCode()).isEqualTo(customer.getCode());

        // Delete the document from the repository
        customerWebshopRepository.delete(customer);

        // Try to find the document again
        Optional<CustomerLocal> retrievedCustomer = customerWebshopRepository.findById(customer.getCode());

        // Assert that the document is not present anymore
        assertThat(retrievedCustomer).isEmpty();
    }
}
