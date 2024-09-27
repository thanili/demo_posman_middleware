package org.example.dest_service.api;

import org.example.dest_service.entity.pos.dto.DocumentDto;
import org.example.dest_service.entity.webshop.dto.DeliveryNoteDto;
import org.example.dest_service.service.ExportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/export")
public class ExportDataController {
    private final ExportDataService exportDataService;

    @Autowired
    public ExportDataController(ExportDataService exportDataService) {
        this.exportDataService = exportDataService;
    }

    @GetMapping("/receipts/webshop")
    public ResponseEntity<List<DeliveryNoteDto>> exportWebshopReceipts() {
        return ResponseEntity.ok(exportDataService.exportDeliveryNotes());
    }

    @GetMapping("/receipts/pos")
    public ResponseEntity<Iterable<DocumentDto>> exportPosStationReceipts() {
        return ResponseEntity.ok(exportDataService.exportDocuments());
    }
}
