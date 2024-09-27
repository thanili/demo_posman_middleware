package org.example.dest_service.repository.webshop;

import org.example.dest_service.entity.webshop.DeliveryNotePositionId;
import org.example.dest_service.entity.webshop.DeliveryNotePositions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryNotePositionsRepository extends JpaRepository<DeliveryNotePositions, DeliveryNotePositionId> {
    List<DeliveryNotePositions> findBySalesReceiptId(String salesReceiptId);
}