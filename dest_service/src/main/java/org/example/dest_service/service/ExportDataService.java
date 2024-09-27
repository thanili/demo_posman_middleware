package org.example.dest_service.service;

import org.example.dest_service.entity.common.CustomerLocal;
import org.example.dest_service.entity.common.dto.CustomerLocalDto;
import org.example.dest_service.entity.pos.Document;
import org.example.dest_service.entity.pos.DocumentPayments;
import org.example.dest_service.entity.pos.DocumentPositions;
import org.example.dest_service.entity.pos.dto.DocumentDto;
import org.example.dest_service.entity.pos.dto.DocumentPaymentsDto;
import org.example.dest_service.entity.pos.dto.DocumentPositionsDto;
import org.example.dest_service.entity.webshop.DeliveryNote;
import org.example.dest_service.entity.webshop.DeliveryNotePayments;
import org.example.dest_service.entity.webshop.DeliveryNotePositions;
import org.example.dest_service.entity.webshop.dto.DeliveryNoteDto;
import org.example.dest_service.entity.webshop.dto.DeliveryNotePaymentsDto;
import org.example.dest_service.entity.webshop.dto.DeliveryNotePositionsDto;
import org.example.dest_service.repository.pos.CustomerPosRepository;
import org.example.dest_service.repository.pos.DocumentPaymentsRepository;
import org.example.dest_service.repository.pos.DocumentPositionsRepository;
import org.example.dest_service.repository.pos.DocumentRepository;
import org.example.dest_service.repository.webshop.CustomerWebshopRepository;
import org.example.dest_service.repository.webshop.DeliveryNotePaymentsRepository;
import org.example.dest_service.repository.webshop.DeliveryNotePositionsRepository;
import org.example.dest_service.repository.webshop.DeliveryNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExportDataService {
    private final DocumentRepository documentRepository;
    private final DocumentPositionsRepository documentPositionsRepository;
    private final DocumentPaymentsRepository documentPaymentsRepository;

    private final DeliveryNoteRepository deliveryNoteRepository;
    private final DeliveryNotePositionsRepository deliveryNotePositionsRepository;
    private final DeliveryNotePaymentsRepository deliveryNotePaymentsRepository;

    private final CustomerPosRepository customerPosRepository;
    private final CustomerWebshopRepository customerWebshopRepository;

    @Autowired
    public ExportDataService(DocumentRepository documentRepository, DocumentPositionsRepository documentPositionsRepository, DocumentPaymentsRepository documentPaymentsRepository, DeliveryNoteRepository deliveryNoteRepository, DeliveryNotePositionsRepository deliveryNotePositionsRepository, DeliveryNotePaymentsRepository deliveryNotePaymentsRepository, CustomerPosRepository customerPosRepository, CustomerWebshopRepository customerWebshopRepository) {
        this.documentRepository = documentRepository;
        this.documentPositionsRepository = documentPositionsRepository;
        this.documentPaymentsRepository = documentPaymentsRepository;
        this.deliveryNoteRepository = deliveryNoteRepository;
        this.deliveryNotePositionsRepository = deliveryNotePositionsRepository;
        this.deliveryNotePaymentsRepository = deliveryNotePaymentsRepository;
        this.customerPosRepository = customerPosRepository;
        this.customerWebshopRepository = customerWebshopRepository;
    }

    public List<DocumentDto> exportDocuments() {
        List<DocumentDto> documentDtos = new ArrayList<>();
        List<Document> documents = documentRepository.findAll();
        for (Document document : documents) {
            DocumentDto documentDto = extractDocumentDto(document);
            documentDtos.add(documentDto);
        }
        return documentDtos;
    }

    public List<DeliveryNoteDto> exportDeliveryNotes() {
        List<DeliveryNoteDto> deliveryNoteDtos = new ArrayList<>();
        List<DeliveryNote> deliveryNotes = deliveryNoteRepository.findAll();
        for(DeliveryNote deliveryNote: deliveryNotes) {
            DeliveryNoteDto deliveryNoteDto = extractDeliveryNoteDto(deliveryNote);
            deliveryNoteDtos.add(deliveryNoteDto);
        }
        return deliveryNoteDtos;
    }

    private DeliveryNoteDto extractDeliveryNoteDto(DeliveryNote deliveryNote) {
        List<DeliveryNotePositions> deliveryNotePositions = deliveryNotePositionsRepository.findBySalesReceiptId(deliveryNote.getSalesReceiptId());
        List<DeliveryNotePayments>  deliveryNotePayments = deliveryNotePaymentsRepository.findBySalesReceiptId(deliveryNote.getSalesReceiptId());
        Optional<CustomerLocal> customerLocal = customerWebshopRepository.findByCode(deliveryNote.getCustomerCode());

        // Create the dto objects
        DeliveryNoteDto deliveryNoteDto = new DeliveryNoteDto();
        DeliveryNoteDto.DeliveryNoteIdDto deliveryNoteIdDto = new DeliveryNoteDto.DeliveryNoteIdDto();

        deliveryNoteIdDto.setServerName(deliveryNote.getId().getServerName());
        deliveryNoteIdDto.setDatabaseName(deliveryNote.getId().getDatabaseName());
        deliveryNoteIdDto.setDate(deliveryNote.getId().getDate());
        deliveryNoteIdDto.setDocumentNo(deliveryNote.getId().getDocumentNo());

        deliveryNoteDto.setId(deliveryNoteIdDto);
        deliveryNoteDto.setCustomerCode(deliveryNote.getCustomerCode());
        deliveryNoteDto.setCustomerId(deliveryNote.getCustomerId());
        deliveryNoteDto.setYear(deliveryNote.getYear());
        deliveryNoteDto.setDeviceNumber(deliveryNote.getDeviceNumber());
        deliveryNoteDto.setSalesReceiptId(deliveryNote.getSalesReceiptId());
        deliveryNoteDto.setExternalReference(deliveryNote.getExternalReference());
        deliveryNoteDto.setReceiptNumber(deliveryNote.getReceiptNumber());
        deliveryNoteDto.setShiftNumber(deliveryNote.getShiftNumber());
        deliveryNoteDto.setSumGross(deliveryNote.getSumGross());
        deliveryNoteDto.setSumNet(deliveryNote.getSumNet());

        List<DeliveryNotePositionsDto> deliveryNotePositionsDtos = new ArrayList<>();
        for(DeliveryNotePositions position: deliveryNotePositions) {
            DeliveryNotePositionsDto deliveryNotePositionsDto = getDeliveryNotePositionsDto(position);
            deliveryNotePositionsDtos.add(deliveryNotePositionsDto);
        }
        deliveryNoteDto.setDeliveryNotePositionsDtos(deliveryNotePositionsDtos);

        List<DeliveryNotePaymentsDto> deliveryNotePaymentsDtos = new ArrayList<>();
        for(DeliveryNotePayments payment: deliveryNotePayments) {
            DeliveryNotePaymentsDto deliveryNotePaymentsDto = getDeliveryNotePaymentsDto(payment);
            deliveryNotePaymentsDtos.add(deliveryNotePaymentsDto);
        }
        deliveryNoteDto.setDeliveryNotePaymentsDtos(deliveryNotePaymentsDtos);

        if(customerLocal.isPresent()) {
            CustomerLocalDto customerLocalDto = getCustomerLocalDto(customerLocal);
            deliveryNoteDto.setCustomerLocalDto(customerLocalDto);
        }

        return deliveryNoteDto;
    }

    private static DeliveryNotePaymentsDto getDeliveryNotePaymentsDto(DeliveryNotePayments payment) {
        DeliveryNotePaymentsDto deliveryNotePaymentsDto = new DeliveryNotePaymentsDto();
        DeliveryNotePaymentsDto.DeliveryNotePaymentIdDto deliveryNotePaymentIdDto = getDeliveryNotePaymentIdDto(payment);

        deliveryNotePaymentsDto.setId(deliveryNotePaymentIdDto);
        deliveryNotePaymentsDto.setSalesReceiptId(payment.getSalesReceiptId());
        deliveryNotePaymentsDto.setPaymentMethodId(payment.getPaymentMethodId());
        deliveryNotePaymentsDto.setPaymentMethodSub(payment.getPaymentMethodSub());
        deliveryNotePaymentsDto.setAmount(payment.getAmount());
        deliveryNotePaymentsDto.setCurrencyCode(payment.getCurrencyCode());
        deliveryNotePaymentsDto.setTransactionNo(payment.getTransactionNo());
        deliveryNotePaymentsDto.setVoucherNo(payment.getVoucherNo());
        return deliveryNotePaymentsDto;
    }

    private static DeliveryNotePositionsDto getDeliveryNotePositionsDto(DeliveryNotePositions position) {
        DeliveryNotePositionsDto deliveryNotePositionsDto = new DeliveryNotePositionsDto();
        DeliveryNotePositionsDto.DeliveryNotePositionIdDto deliveryNotePositionIdDto = getDeliveryNotePositionIdDto(position);

        deliveryNotePositionsDto.setId(deliveryNotePositionIdDto);
        deliveryNotePositionsDto.setArticleCode(position.getArticleCode());
        deliveryNotePositionsDto.setArticleId(position.getArticleId());
        deliveryNotePositionsDto.setArticleGroupCode(position.getArticleGroupCode());
        deliveryNotePositionsDto.setUnitPrice(position.getUnitPrice());
        deliveryNotePositionsDto.setPrice(position.getPrice());
        deliveryNotePositionsDto.setAmount(position.getAmount());
        deliveryNotePositionsDto.setVatAmount(position.getVatAmount());
        deliveryNotePositionsDto.setVatCode(position.getVatCode());
        deliveryNotePositionsDto.setVatRate(position.getVatRate());
        deliveryNotePositionsDto.setSalesReceiptId(position.getSalesReceiptId());
        deliveryNotePositionsDto.setSalesReceiptDate(position.getSalesReceiptDate());
        deliveryNotePositionsDto.setTicketNumber(position.getTicketNumber());
        deliveryNotePositionsDto.setTotalDiscount(position.getTotalDiscount());
        return deliveryNotePositionsDto;
    }

    private static DeliveryNotePositionsDto.DeliveryNotePositionIdDto getDeliveryNotePositionIdDto(DeliveryNotePositions position) {
        DeliveryNotePositionsDto.DeliveryNotePositionIdDto deliveryNotePositionIdDto = new DeliveryNotePositionsDto.DeliveryNotePositionIdDto();

        deliveryNotePositionIdDto.setServerName(position.getId().getServerName());
        deliveryNotePositionIdDto.setDatabaseName(position.getId().getDatabaseName());
        deliveryNotePositionIdDto.setDate(position.getId().getDate());
        deliveryNotePositionIdDto.setDocumentNo(position.getId().getDocumentNo());
        deliveryNotePositionIdDto.setPositionNo(position.getId().getPositionNo());
        return deliveryNotePositionIdDto;
    }

    private static DeliveryNotePaymentsDto.DeliveryNotePaymentIdDto getDeliveryNotePaymentIdDto(DeliveryNotePayments payment) {
        DeliveryNotePaymentsDto.DeliveryNotePaymentIdDto deliveryNotePaymentIdDto = new DeliveryNotePaymentsDto.DeliveryNotePaymentIdDto();

        deliveryNotePaymentIdDto.setServerName(payment.getId().getServerName());
        deliveryNotePaymentIdDto.setDatabaseName(payment.getId().getDatabaseName());
        deliveryNotePaymentIdDto.setDocumentNo(payment.getId().getDocumentNo());
        deliveryNotePaymentIdDto.setPositionNo(payment.getId().getPositionNo());
        deliveryNotePaymentIdDto.setDate(payment.getId().getDate());
        return deliveryNotePaymentIdDto;
    }

    private DocumentDto extractDocumentDto(Document document) {
        List<DocumentPositions> documentPositions = documentPositionsRepository.findBySalesReceiptId(document.getSalesReceiptId());
        List<DocumentPayments> documentPayments = documentPaymentsRepository.findBySalesReceiptId(document.getSalesReceiptId());
        Optional<CustomerLocal> customerLocal = customerPosRepository.findByCode(document.getCustomerCode());

        // Create the dto objects
        DocumentDto documentDto = new DocumentDto();
        DocumentDto.DocumentIdDto documentIdDto = new DocumentDto.DocumentIdDto();

        documentIdDto.setServerName(document.getId().getServerName());
        documentIdDto.setDatabaseName(document.getId().getDatabaseName());
        documentIdDto.setCashRegisterId(document.getId().getCashRegisterId());
        documentIdDto.setDate(document.getId().getDate());
        documentIdDto.setDocumentNo(document.getId().getDocumentNo());

        documentDto.setId(documentIdDto);
        documentDto.setSalesReceiptId(document.getSalesReceiptId());
        documentDto.setCashierId(document.getCashierId());
        documentDto.setCountryCode(document.getCountryCode());
        documentDto.setCustomerCode(document.getCustomerCode());
        documentDto.setZipCode(document.getZipCode());
        documentDto.setTicketNo(document.getTicketNo());
        documentDto.setDocumentDateTime(document.getDocumentDateTime());
        documentDto.setType(document.getType());
        documentDto.setSumGross(document.getSumGross());
        documentDto.setSumNet(document.getSumNet());

        List<DocumentPositionsDto> documentPositionsDtos = new ArrayList<>();
        for(DocumentPositions position: documentPositions) {
            DocumentPositionsDto documentPositionsDto = getDocumentPositionsDto(position);
            documentPositionsDtos.add(documentPositionsDto);
        }
        documentDto.setDocumentPositionsDtos(documentPositionsDtos);

        List<DocumentPaymentsDto> documentPaymentsDtos = new ArrayList<>();
        for(DocumentPayments payment: documentPayments) {
            DocumentPaymentsDto documentPaymentsDto = getDocumentPaymentsDto(payment);
            documentPaymentsDtos.add(documentPaymentsDto);
        }
        documentDto.setDocumentPaymentsDtos(documentPaymentsDtos);

        if(customerLocal.isPresent()) {
            CustomerLocalDto customerLocalDto = getCustomerLocalDto(customerLocal);
            documentDto.setCustomerLocalDto(customerLocalDto);
        }

        return documentDto;
    }

    private static DocumentPositionsDto getDocumentPositionsDto(DocumentPositions position) {
        DocumentPositionsDto documentPositionsDto = new DocumentPositionsDto();
        DocumentPositionsDto.DocumentPositionIdDto documentPositionIdDto = getDocumentPositionIdDto(position);

        documentPositionsDto.setId(documentPositionIdDto);
        documentPositionsDto.setSalesReceiptId(position.getSalesReceiptId());
        documentPositionsDto.setArticleCode(position.getArticleCode());
        documentPositionsDto.setArticleGroupCode(position.getArticleGroupCode());
        documentPositionsDto.setUnitPrice(position.getUnitPrice());
        documentPositionsDto.setPrice(position.getPrice());
        documentPositionsDto.setAmount(position.getAmount());
        documentPositionsDto.setVatAmount(position.getVatAmount());
        documentPositionsDto.setVatCode(position.getVatCode());
        documentPositionsDto.setVatRate(position.getVatRate());
        documentPositionsDto.setDiscount(position.getDiscount());
        documentPositionsDto.setType(position.getType());
        documentPositionsDto.setBarcode(position.getBarcode());
        documentPositionsDto.setArticleName(position.getArticleName());
        documentPositionsDto.setVoucherNo(position.getVoucherNo());
        return documentPositionsDto;
    }

    private static DocumentPaymentsDto getDocumentPaymentsDto(DocumentPayments payment) {
        DocumentPaymentsDto documentPaymentsDto = new DocumentPaymentsDto();
        DocumentPaymentsDto.DocumentPaymentIdDto documentPaymentIdDto = getDocumentPaymentIdDto(payment);

        documentPaymentsDto.setId(documentPaymentIdDto);
        documentPaymentsDto.setSalesReceiptId(payment.getSalesReceiptId());
        documentPaymentsDto.setPaymentMethodId(payment.getPaymentMethodId());
        documentPaymentsDto.setPaymentMethodSub(payment.getPaymentMethodSub());
        documentPaymentsDto.setAmount(payment.getAmount());
        documentPaymentsDto.setCurrencyCode(payment.getCurrencyCode());
        documentPaymentsDto.setTransactionReferenceNo(payment.getTransactionReferenceNo());
        documentPaymentsDto.setVoucherNo(payment.getVoucherNo());
        documentPaymentsDto.setProvider(payment.getProvider());
        documentPaymentsDto.setCardNo(payment.getCardNo());
        documentPaymentsDto.setIsChange(payment.getIsChange());
        return documentPaymentsDto;
    }

    private static CustomerLocalDto getCustomerLocalDto(Optional<CustomerLocal> customerLocal) {
        CustomerLocalDto customerLocalDto = new CustomerLocalDto();
        customerLocalDto.setCode(String.valueOf(customerLocal.get().getCode()));
        customerLocalDto.setSalutation(customerLocal.get().getSalutation());
        customerLocalDto.setFirstName(customerLocal.get().getFirstName());
        customerLocalDto.setLastName(customerLocal.get().getLastName());
        customerLocalDto.setLocation(customerLocal.get().getLocation());
        customerLocalDto.setStreet(customerLocal.get().getStreet());
        customerLocalDto.setZipCode(customerLocal.get().getZipCode());
        customerLocalDto.setCountryCode(customerLocal.get().getCountryCode());
        customerLocalDto.setCompany(customerLocal.get().getCompany());
        customerLocalDto.setDateOfBirth(LocalDate.from(customerLocal.get().getDateOfBirth()));
        customerLocalDto.setEMail(customerLocal.get().getEMail());
        return customerLocalDto;
    }

    private static DocumentPaymentsDto.DocumentPaymentIdDto getDocumentPaymentIdDto(DocumentPayments payment) {
        DocumentPaymentsDto.DocumentPaymentIdDto documentPaymentIdDto = new DocumentPaymentsDto.DocumentPaymentIdDto();

        documentPaymentIdDto.setServerName(payment.getId().getServerName());
        documentPaymentIdDto.setDatabaseName(payment.getId().getDatabaseName());
        documentPaymentIdDto.setCashRegisterId(payment.getId().getCashRegisterId());
        documentPaymentIdDto.setDocumentNo(payment.getId().getDocumentNo());
        documentPaymentIdDto.setDatum(payment.getId().getDatum());
        documentPaymentIdDto.setPositionNo(payment.getId().getPositionNo());
        return documentPaymentIdDto;
    }

    private static DocumentPositionsDto.DocumentPositionIdDto getDocumentPositionIdDto(DocumentPositions position) {
        DocumentPositionsDto.DocumentPositionIdDto documentPositionIdDto = new DocumentPositionsDto.DocumentPositionIdDto();
        documentPositionIdDto.setServerName(position.getId().getServerName());
        documentPositionIdDto.setDatabaseName(position.getId().getDatabaseName());
        documentPositionIdDto.setCashRegisterId(position.getId().getCashRegisterId());
        documentPositionIdDto.setDate(position.getId().getDate());
        documentPositionIdDto.setDocumentNo(position.getId().getDocumentNo());
        documentPositionIdDto.setPositionNo(position.getId().getPositionNo());
        return documentPositionIdDto;
    }
}
