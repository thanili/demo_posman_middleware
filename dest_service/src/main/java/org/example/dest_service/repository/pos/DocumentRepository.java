package org.example.dest_service.repository.pos;

import org.example.dest_service.entity.pos.Document;
import org.example.dest_service.entity.pos.DocumentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, DocumentId> {
}