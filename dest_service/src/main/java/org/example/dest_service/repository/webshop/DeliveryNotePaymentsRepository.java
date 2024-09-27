package org.example.dest_service.repository.webshop;

import org.example.dest_service.entity.webshop.DeliveryNotePaymentId;
import org.example.dest_service.entity.webshop.DeliveryNotePayments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryNotePaymentsRepository extends JpaRepository<DeliveryNotePayments, DeliveryNotePaymentId> {
    List<DeliveryNotePayments> findBySalesReceiptId(String salesReceiptId);
}