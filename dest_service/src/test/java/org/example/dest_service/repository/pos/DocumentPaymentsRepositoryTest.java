package org.example.dest_service.repository.pos;

import org.example.dest_service.DestServiceApplication;
import org.example.dest_service.configuration.datasource.PosDataSourceConfiguration;
import org.example.dest_service.configuration.datasource.WebshopDataSourceConfiguration;
import org.example.dest_service.entity.pos.DocumentPaymentId;
import org.example.dest_service.entity.pos.DocumentPayments;
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
public class DocumentPaymentsRepositoryTest {
    @Autowired
    DocumentPaymentsRepository documentPaymentsRepository;

    private DocumentPayments createDocumentPayments() {
        DocumentPaymentId id = new DocumentPaymentId();
        id.setDocumentNo(123);
        id.setCashRegisterId(1);
        id.setDatabaseName("Db_01");
        id.setServerName("Server_01");
        id.setPositionNo(1);
        id.setDatum(LocalDate.now());

        DocumentPayments documentPayments = new DocumentPayments();
        documentPayments.setId(id);
        documentPayments.setPaymentMethodId(1);
        documentPayments.setPaymentMethodSub(1);
        documentPayments.setAmount(BigDecimal.valueOf(119l));
        documentPayments.setProvider("Provider_01");
        documentPayments.setCardNo("2323 3232 3432 3331");
        documentPayments.setCurrencyCode("US");
        documentPayments.setIsChange(0);
        documentPayments.setVoucherNo("0");
        documentPayments.setTransactionReferenceNo("231321");

        return documentPayments;
    }

    @Test
    public void test_DocumentPaymentsRepository_Save_FindById_Delete() {
        DocumentPayments documentPayments = createDocumentPayments();

        // Save the document
        documentPaymentsRepository.save(documentPayments);
        // Try to find the document by its ID
        Optional<DocumentPayments> foundDocumentPayments = documentPaymentsRepository.findById(documentPayments.getId());

        // Check if the document is present
        assertThat(foundDocumentPayments).isPresent();

        // Validate the found document
        assertThat(foundDocumentPayments.get().getId()).isEqualTo(documentPayments.getId());

        // Delete the document from the repository
        documentPaymentsRepository.delete(documentPayments);

        // Try to find the document again
        Optional<DocumentPayments> retrievedDocumentPayments = documentPaymentsRepository.findById(documentPayments.getId());

        // Assert that the document is not present anymore
        assertThat(retrievedDocumentPayments).isEmpty();
    }
}

