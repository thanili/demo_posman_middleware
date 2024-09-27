package org.example.dest_service.repository.pos;

import org.example.dest_service.entity.pos.DocumentPaymentId;
import org.example.dest_service.entity.pos.DocumentPayments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentPaymentsRepository extends JpaRepository<DocumentPayments, DocumentPaymentId> {
    /**
     * Finds all DocumentPayments by SalesReceiptId.
     *
     * @param salesReceiptId the SalesReceiptId to search for
     * @return a list of DocumentPayments with the specified SalesReceiptId
     */
    List<DocumentPayments> findBySalesReceiptId(String salesReceiptId);
}