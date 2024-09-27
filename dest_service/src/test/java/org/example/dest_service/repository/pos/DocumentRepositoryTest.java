package org.example.dest_service.repository.pos;

import org.example.dest_service.DestServiceApplication;
import org.example.dest_service.configuration.datasource.PosDataSourceConfiguration;
import org.example.dest_service.configuration.datasource.WebshopDataSourceConfiguration;
import org.example.dest_service.entity.pos.Document;
import org.example.dest_service.entity.pos.DocumentId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DestServiceApplication.class, PosDataSourceConfiguration.class, WebshopDataSourceConfiguration.class})  // Full application context
public class DocumentRepositoryTest {
    @Autowired
    private DocumentRepository documentRepository;

    private Document createDocument() {
        // Create a DocumentId instance
        DocumentId documentId = new DocumentId();
        documentId.setServerName("Server1");
        documentId.setDatabaseName("DB1_test");
        documentId.setCashRegisterId(1);
        documentId.setDate(LocalDate.of(2024, 9, 16));
        documentId.setDocumentNo(1001);

        // Create a Document instance
        Document document = new Document();
        document.setId(documentId);
        document.setCashierId("Cashier001");
        document.setCountryCode("US");
        document.setCustomerCode("CUST123");
        document.setZipCode("10001");
        document.setTicketNo("TICKET001");
        document.setDocumentDateTime(LocalDateTime.now());
        document.setType((short) 1);
        document.setSumGross(new BigDecimal("100.50"));
        document.setSumNet(new BigDecimal("90.45"));
        document.setSalesReceiptId(String.valueOf(java.util.UUID.randomUUID()));

        return document;
    }

    @Test
    public void test_DocumentRepo_Save_FindById_Delete() {
        Document doc = createDocument();

        // Save the document
        documentRepository.save(doc);
        // Try to find the document by its ID
        Optional<Document> foundDocument = documentRepository.findById(doc.getId());

        // Check if the document is present
        assertThat(foundDocument).isPresent();

        // Validate the found document
        assertThat(foundDocument.get().getId()).isEqualTo(doc.getId());
        assertThat(foundDocument.get().getCashierId()).isEqualTo("Cashier001");

        // Delete the document from the repository
        documentRepository.delete(doc);

        // Try to find the document again
        Optional<Document> foundDeletedDocument = documentRepository.findById(doc.getId());

        // Assert that the document is not present anymore
        assertThat(foundDeletedDocument).isEmpty();
    }
}
