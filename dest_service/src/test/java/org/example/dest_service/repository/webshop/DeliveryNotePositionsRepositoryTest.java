package org.example.dest_service.repository.webshop;

import org.example.dest_service.DestServiceApplication;
import org.example.dest_service.configuration.datasource.PosDataSourceConfiguration;
import org.example.dest_service.configuration.datasource.WebshopDataSourceConfiguration;
import org.example.dest_service.entity.webshop.DeliveryNotePositionId;
import org.example.dest_service.entity.webshop.DeliveryNotePositions;
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
public class DeliveryNotePositionsRepositoryTest {
    @Autowired
    DeliveryNotePositionsRepository deliveryNotePositionsRepository;

    private DeliveryNotePositions createDeliveryNotePositions() {
        DeliveryNotePositionId id = new DeliveryNotePositionId();
        id.setDocumentNo(1);
        id.setPositionNo(1);
        id.setDatabaseName("Db_01");
        id.setServerName("Server_01");
        id.setDate(LocalDate.now());

        DeliveryNotePositions deliveryNotePositions = new DeliveryNotePositions();
        deliveryNotePositions.setId(id);
        deliveryNotePositions.setArticleId("13123123");
        deliveryNotePositions.setArticleCode("32423423");
        deliveryNotePositions.setArticleGroupCode("2");
        deliveryNotePositions.setPrice(BigDecimal.valueOf(3.33));
        deliveryNotePositions.setAmount(BigDecimal.valueOf(23.33));
        deliveryNotePositions.setUnitPrice(BigDecimal.TEN);
        deliveryNotePositions.setTotalDiscount(BigDecimal.ZERO);
        deliveryNotePositions.setVatAmount(BigDecimal.valueOf(12.2));
        deliveryNotePositions.setVatCode("2");
        deliveryNotePositions.setVatRate(BigDecimal.valueOf(21));
        deliveryNotePositions.setTicketNumber("");
        deliveryNotePositions.setSalesReceiptId("32423");
        deliveryNotePositions.setType(1);
        deliveryNotePositions.setVoucherNo("0");

        return deliveryNotePositions;
    }

    @Test
    public void test_DeliveryNotePositionsRepository_Save_FindById_Delete() {
        DeliveryNotePositions deliveryNotePositions = createDeliveryNotePositions();

        // Save the document
        deliveryNotePositionsRepository.save(deliveryNotePositions);
        // Try to find the document by its ID
        Optional<DeliveryNotePositions> positionsOptional = deliveryNotePositionsRepository.findById(deliveryNotePositions.getId());

        // Check if the document is present
        assertThat(positionsOptional).isPresent();

        // Validate the found document
        assertThat(positionsOptional.get().getId()).isEqualTo(deliveryNotePositions.getId());

        // Delete the document from the repository
        deliveryNotePositionsRepository.delete(deliveryNotePositions);

        // Try to find the document again
        Optional<DeliveryNotePositions> foundDeliveryNotePositions = deliveryNotePositionsRepository.findById(deliveryNotePositions.getId());

        // Assert that the document is not present anymore
        assertThat(foundDeliveryNotePositions).isEmpty();
    }
}
