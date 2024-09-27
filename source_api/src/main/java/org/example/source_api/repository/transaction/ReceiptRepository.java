package org.example.source_api.repository.transaction;

import org.example.source_api.entity.transaction.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}