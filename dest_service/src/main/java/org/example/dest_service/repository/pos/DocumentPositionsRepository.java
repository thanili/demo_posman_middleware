package org.example.dest_service.repository.pos;

import org.example.dest_service.entity.pos.DocumentPositionId;
import org.example.dest_service.entity.pos.DocumentPositions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentPositionsRepository extends JpaRepository<DocumentPositions, DocumentPositionId> {
    /**
     * Finds all DocumentPositions by SalesReceiptId.
     *
     * @param salesReceiptId the SalesReceiptId to search for
     * @return a list of DocumentPositions with the specified SalesReceiptId
     */
    List<DocumentPositions> findBySalesReceiptId(String salesReceiptId);
}