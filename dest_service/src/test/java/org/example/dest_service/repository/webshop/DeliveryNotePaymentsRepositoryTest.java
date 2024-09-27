package org.example.dest_service.repository.webshop;

import org.example.dest_service.DestServiceApplication;
import org.example.dest_service.configuration.datasource.PosDataSourceConfiguration;
import org.example.dest_service.configuration.datasource.WebshopDataSourceConfiguration;
import org.example.dest_service.entity.webshop.DeliveryNotePaymentId;
import org.example.dest_service.entity.webshop.DeliveryNotePayments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DestServiceApplication.class, PosDataSourceConfiguration.class, WebshopDataSourceConfiguration.class})
public class DeliveryNotePaymentsRepositoryTest {
    @Autowired
    DeliveryNotePaymentsRepository deliveryNotePaymentsRepository;

    private DeliveryNotePayments createDeliveryNotePayments() {
        DeliveryNotePaymentId id = new DeliveryNotePaymentId();
        id.setDocumentNo(123);
        id.setDatabaseName("Db_01");
        id.setServerName("Server_01");
        id.setPositionNo(1);
        id.setDate(LocalDate.now());

        DeliveryNotePayments deliveryNotePayments = new DeliveryNotePayments();
        deliveryNotePayments.setId(id);
        deliveryNotePayments.setPaymentMethodId(1);
        deliveryNotePayments.setPaymentMethodSub(1);
        deliveryNotePayments.setAmount(BigDecimal.valueOf(119l));
        deliveryNotePayments.setTransactionNo("231321");
        deliveryNotePayments.setCurrencyCode("US");
        deliveryNotePayments.setVoucherNo("0");

        return deliveryNotePayments;
    }

    @Test
    public void test_DeliveryNotePaymentsRepository_Save_FindById_Delete() {
        DeliveryNotePayments documentPayments = createDeliveryNotePayments();

        // Save the document
        deliveryNotePaymentsRepository.save(documentPayments);
        // Try to find the document by its ID
        Optional<DeliveryNotePayments> deliveryNotePayments = deliveryNotePaymentsRepository.findById(documentPayments.getId());

        // Check if the document is present
        assertThat(deliveryNotePayments).isPresent();

        // Validate the found document
        assertThat(deliveryNotePayments.get().getId()).isEqualTo(documentPayments.getId());

        // Delete the document from the repository
        deliveryNotePaymentsRepository.delete(documentPayments);

        // Try to find the document again
        Optional<DeliveryNotePayments> optionalDeliveryNotePayments = deliveryNotePaymentsRepository.findById(documentPayments.getId());

        // Assert that the document is not present anymore
        assertThat(optionalDeliveryNotePayments).isEmpty();
    }
}
