package org.example.source_api.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.dto.receipt.ReceiptDto;
import org.example.source_api.entity.transaction.Receipt;
import org.example.source_api.repository.transaction.ReceiptRepository;
import org.example.source_api.service.ReceiptDataTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceScheduler {

    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    ReceiptDataTransferService receiptDataTransferService;
    @Autowired
    ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 120000)
    public void sendReceipts() {
        List<Receipt> receipts = receiptRepository.findAll();
        receipts.forEach(receipt -> {
            try {
                ReceiptDto receiptDto = objectMapper.readValue(receipt.getReceiptJson(), ReceiptDto.class);
                receiptDataTransferService.postReceiptData(receiptDto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
