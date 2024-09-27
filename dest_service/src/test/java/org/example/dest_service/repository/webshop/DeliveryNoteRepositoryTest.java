package org.example.dest_service.repository.webshop;

import org.example.dest_service.DestServiceApplication;
import org.example.dest_service.configuration.datasource.PosDataSourceConfiguration;
import org.example.dest_service.configuration.datasource.WebshopDataSourceConfiguration;
import org.example.dest_service.entity.webshop.DeliveryNote;
import org.example.dest_service.entity.webshop.DeliveryNoteId;
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
public class DeliveryNoteRepositoryTest {
    @Autowired
    DeliveryNoteRepository deliveryNoteRepository;

    private DeliveryNote createDeliveryNote() {
        DeliveryNoteId id = new DeliveryNoteId();
        id.setDocumentNo(123);
        id.setDatabaseName("Db_01");
        id.setServerName("Server_01");
        id.setDate(LocalDate.now());

        DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setId(id);
        deliveryNote.setCustomerCode("C0011");
        deliveryNote.setCustomerId("3423");
        deliveryNote.setYear(2024);
        deliveryNote.setDeviceNumber("1");
        deliveryNote.setSalesReceiptId("111");
        deliveryNote.setExternalReference(112);
        deliveryNote.setReceiptNumber(111);
        deliveryNote.setShiftNumber("22");
        deliveryNote.setSumGross(BigDecimal.valueOf(23.54));
        deliveryNote.setSumNet(BigDecimal.valueOf(23.54));

        return deliveryNote;
    }

    @Test
    public void test_DocumentRepo_Save_FindById_Delete() {
        DeliveryNote doc = createDeliveryNote();

        // Save the document
        deliveryNoteRepository.save(doc);
        // Try to find the document by its ID
        Optional<DeliveryNote> foundDeliveryNote = deliveryNoteRepository.findById(doc.getId());

        // Check if the document is present
        assertThat(foundDeliveryNote).isPresent();

        // Validate the found document
        assertThat(foundDeliveryNote.get().getId()).isEqualTo(doc.getId());
        assertThat(foundDeliveryNote.get().getCustomerCode()).isEqualTo("C0011");

        // Delete the document from the repository
        deliveryNoteRepository.delete(doc);

        // Try to find the document again
        Optional<DeliveryNote> optDeliveryNote = deliveryNoteRepository.findById(doc.getId());

        // Assert that the document is not present anymore
        assertThat(optDeliveryNote).isEmpty();
    }
}
