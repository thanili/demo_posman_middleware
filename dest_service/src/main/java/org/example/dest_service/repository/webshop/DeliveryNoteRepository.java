package org.example.dest_service.repository.webshop;

import org.example.dest_service.entity.webshop.DeliveryNote;
import org.example.dest_service.entity.webshop.DeliveryNoteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, DeliveryNoteId> {
}