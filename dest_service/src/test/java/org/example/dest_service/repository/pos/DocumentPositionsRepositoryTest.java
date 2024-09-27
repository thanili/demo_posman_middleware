package org.example.dest_service.repository.pos;

import org.example.dest_service.DestServiceApplication;
import org.example.dest_service.configuration.datasource.PosDataSourceConfiguration;
import org.example.dest_service.configuration.datasource.WebshopDataSourceConfiguration;
import org.example.dest_service.entity.pos.DocumentPositionId;
import org.example.dest_service.entity.pos.DocumentPositions;
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
public class DocumentPositionsRepositoryTest {
    @Autowired
    DocumentPositionsRepository documentPositionsRepository;

    private DocumentPositions createDocumentPositions() {
        DocumentPositionId id = new DocumentPositionId();
        id.setDocumentNo(1);
        id.setCashRegisterId(1);
        id.setPositionNo(1);
        id.setDatabaseName("Db_01");
        id.setServerName("Server_01");
        id.setDate(LocalDate.now());

        DocumentPositions documentPositions = new DocumentPositions();
        documentPositions.setId(id);
        documentPositions.setAmount(BigDecimal.valueOf(23.33));
        documentPositions.setType(1);
        documentPositions.setBarcode("123124324324");
        documentPositions.setDiscount(BigDecimal.ZERO);
        documentPositions.setPrice(BigDecimal.valueOf(3.33));
        documentPositions.setArticleCode("32423423");
        documentPositions.setArticleGroupCode("2");
        documentPositions.setArticleName("Test article");
        documentPositions.setUnitPrice(BigDecimal.TEN);
        documentPositions.setVatAmount(BigDecimal.valueOf(12.2));
        documentPositions.setVatCode("2");
        documentPositions.setVatRate(BigDecimal.valueOf(21));
        documentPositions.setVoucherNo("0");

        return documentPositions;
    }

    @Test
    public void test_DocumentPositionsRepository_Save_FindById_Delete() {
        DocumentPositions documentPositions = createDocumentPositions();

        // Save the document
        documentPositionsRepository.save(documentPositions);
        // Try to find the document by its ID
        Optional<DocumentPositions> documentPositionsFound = documentPositionsRepository.findById(documentPositions.getId());

        // Check if the document is present
        assertThat(documentPositionsFound).isPresent();

        // Validate the found document
        assertThat(documentPositionsFound.get().getId()).isEqualTo(documentPositions.getId());

        // Delete the document from the repository
        documentPositionsRepository.delete(documentPositions);

        // Try to find the document again
        Optional<DocumentPositions> retrievedDocumentPositions = documentPositionsRepository.findById(documentPositions.getId());

        // Assert that the document is not present anymore
        assertThat(retrievedDocumentPositions).isEmpty();
    }
}
