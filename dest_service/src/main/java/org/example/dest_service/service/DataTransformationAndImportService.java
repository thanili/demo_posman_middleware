package org.example.dest_service.service;

import org.example.api.dto.receipt.ArticleDto;
import org.example.api.dto.receipt.PaymentDto;
import org.example.api.dto.receipt.ReceiptDto;
import org.example.api.entity.Customer;
import org.example.dest_service.client.RestApiClientService;
import org.example.dest_service.entity.common.CustomerLocal;
import org.example.dest_service.entity.pos.*;
import org.example.dest_service.entity.webshop.*;
import org.example.dest_service.repository.pos.CustomerPosRepository;
import org.example.dest_service.repository.pos.DocumentPaymentsRepository;
import org.example.dest_service.repository.pos.DocumentPositionsRepository;
import org.example.dest_service.repository.pos.DocumentRepository;
import org.example.dest_service.repository.webshop.CustomerWebshopRepository;
import org.example.dest_service.repository.webshop.DeliveryNotePaymentsRepository;
import org.example.dest_service.repository.webshop.DeliveryNotePositionsRepository;
import org.example.dest_service.repository.webshop.DeliveryNoteRepository;
import org.example.dest_service.service.model.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataTransformationAndImportService {

    private static final Logger logger = LoggerFactory.getLogger(DataTransformationAndImportService.class);

    private static int shiftNumberMultiplier = 100000;

    @Value("${org.example.service.webshop.server-name}")
    private String serverNameWebshop;
    @Value("${org.example.service.webshop.database}")
    private String databaseNameWebshop;
    @Value("${org.example.service.pos.server-name}")
    private String serverNamePos;
    @Value("${org.example.service.pos.database}")
    private String databaseNamePos;

    private final DeliveryNoteRepository deliveryNoteRepository;
    private final DeliveryNotePositionsRepository deliveryNotePositionsRepository;
    private final DeliveryNotePaymentsRepository deliveryNotePaymentsRepository;
    private final CustomerWebshopRepository customerWebshopRepository;

    private final DocumentRepository documentRepository;
    private final DocumentPositionsRepository documentPositionsRepository;
    private final DocumentPaymentsRepository documentPaymentsRepository;
    private final CustomerPosRepository customerPosRepository;

    private final RestApiClientService restApiClientService;

    @Autowired
    public DataTransformationAndImportService(DeliveryNoteRepository deliveryNoteRepository,
                                              DeliveryNotePositionsRepository deliveryNotePositionsRepository,
                                              DeliveryNotePaymentsRepository deliveryNotePaymentsRepository,
                                              CustomerWebshopRepository customerWebshopRepository,
                                              DocumentRepository documentRepository,
                                              DocumentPositionsRepository documentPositionsRepository,
                                              DocumentPaymentsRepository documentPaymentsRepository,
                                              CustomerPosRepository customerPosRepository,
                                              RestApiClientService restApiClientService) {
        this.deliveryNoteRepository = deliveryNoteRepository;
        this.deliveryNotePositionsRepository = deliveryNotePositionsRepository;
        this.deliveryNotePaymentsRepository = deliveryNotePaymentsRepository;
        this.documentRepository = documentRepository;
        this.documentPositionsRepository = documentPositionsRepository;
        this.documentPaymentsRepository = documentPaymentsRepository;
        this.customerPosRepository = customerPosRepository;
        this.customerWebshopRepository = customerWebshopRepository;
        this.restApiClientService = restApiClientService;
    }

    @Transactional
    public void transformReceiptDataAndSaveWebshop(ReceiptDto receipt){
        // DeliveryNote
        DeliveryNote deliveryNote = extractDeliveryNote(receipt);
        // DeliveryNotePositions
        List<DeliveryNotePositions> deliveryNotePositions = extractDeliveryNotePositions(receipt);
        // DeliveryNotePayments
        List<DeliveryNotePayments> deliveryNotePayments = extractDeliveryNotePayments(receipt);

        deliveryNoteRepository.save(deliveryNote);
        deliveryNotePositionsRepository.saveAll(deliveryNotePositions);
        deliveryNotePaymentsRepository.saveAll(deliveryNotePayments);

        saveReceiptCustomer(receipt,"webshop");
    }

    @Transactional
    public void transformReceiptDataAndSavePos(ReceiptDto receipt){
        // Document
        Document document = extractDocument(receipt);
        // DocumentPositions
        List<DocumentPositions> documentPositions = extractDocumentPositions(receipt);
        // DocumentPayments
        List<DocumentPayments> documentPayments = extractDocumentPayments(receipt);

        documentRepository.save(document);
        documentPositionsRepository.saveAll(documentPositions);
        documentPaymentsRepository.saveAll(documentPayments);

        // Find receipt customer
        saveReceiptCustomer(receipt, "pos");
    }

    private DeliveryNote extractDeliveryNote(ReceiptDto receipt){
        int documentNo = getDocumentNumber(receipt);

        DeliveryNoteId id = new DeliveryNoteId();
        id.setServerName(serverNameWebshop);
        id.setDatabaseName(databaseNameWebshop);
        id.setDate(receipt.getReceiptDate().toLocalDate());
        id.setDocumentNo(documentNo);

        DeliveryNote deliveryNote = new DeliveryNote();
        deliveryNote.setId(id);
        deliveryNote.setCustomerCode(receipt.getCustomerID());
        deliveryNote.setCustomerId(receipt.getCustomerID());
        deliveryNote.setYear(receipt.getYear());
        deliveryNote.setDeviceNumber(String.valueOf(receipt.getDeviceNumber()));
        deliveryNote.setSalesReceiptId(receipt.getReceiptID());
        deliveryNote.setExternalReference(receipt.getExternalReference());
        deliveryNote.setReceiptNumber(receipt.getReceiptNumber());
        deliveryNote.setShiftNumber(String.valueOf(receipt.getShiftNumber()));
        deliveryNote.setSumGross(BigDecimal.valueOf(receipt.getSumGross()));
        deliveryNote.setSumNet(BigDecimal.valueOf(receipt.getSumNet()));

        return deliveryNote;
    }

    private List<DeliveryNotePayments> extractDeliveryNotePayments(ReceiptDto receipt){
        int documentNo = getDocumentNumber(receipt);

        List<PaymentDto> payments = receipt.getPayments();
        List<DeliveryNotePayments> deliveryNotePayments = new ArrayList<>();
        int i = 0;
        for(PaymentDto payment : payments){
            PaymentMethod paymentMethod = getPaymentMethodId(payment.getType(), payment.getSubtype());

            DeliveryNotePaymentId id = new DeliveryNotePaymentId();
            id.setServerName(serverNameWebshop);
            id.setDatabaseName(databaseNameWebshop);
            id.setDocumentNo(documentNo);
            id.setPositionNo(i+1);
            id.setDate(receipt.getReceiptDate().toLocalDate());

            DeliveryNotePayments deliveryNotePayment = new DeliveryNotePayments();
            deliveryNotePayment.setId(id);
            deliveryNotePayment.setSalesReceiptId(receipt.getReceiptID());
            deliveryNotePayment.setPaymentMethodId(paymentMethod.getId());
            deliveryNotePayment.setPaymentMethodSub(paymentMethod.getSub());
            deliveryNotePayment.setAmount(BigDecimal.valueOf(payment.getValue()));
            deliveryNotePayment.setCurrencyCode("EUR");
            deliveryNotePayment.setTransactionNo(payment.getExternalReference());
            deliveryNotePayment.setVoucherNo(payment.getType() == 5 ? payment.getMediaID() : null);

            deliveryNotePayments.add(deliveryNotePayment);

            i++;
        }

        return deliveryNotePayments;
    }

    private List<DeliveryNotePositions> extractDeliveryNotePositions(ReceiptDto receipt){
        int documentNo = getDocumentNumber(receipt);

        List<ArticleDto> articles = receipt.getArticles();
        List<DeliveryNotePositions> deliveryNotePositions = new ArrayList<>();
        int i = 0;
        for(ArticleDto article : articles){
            DeliveryNotePositionId id = new DeliveryNotePositionId();
            id.setServerName(serverNameWebshop);
            id.setDatabaseName(databaseNameWebshop);
            id.setDocumentNo(documentNo);
            id.setPositionNo(i+1);
            id.setDate(receipt.getReceiptDate().toLocalDate());

            DeliveryNotePositions deliveryNotePosition = new DeliveryNotePositions();
            deliveryNotePosition.setId(id);
            deliveryNotePosition.setArticleCode(article.getArticleID());
            deliveryNotePosition.setArticleId(article.getArticleID());
            deliveryNotePosition.setArticleGroupCode("");
            deliveryNotePosition.setUnitPrice(BigDecimal.valueOf(article.getUnitPrice()));
            deliveryNotePosition.setPrice(BigDecimal.valueOf(article.getTotalPrice()));
            deliveryNotePosition.setAmount(BigDecimal.valueOf(article.getAmount()));
            deliveryNotePosition.setVatAmount(BigDecimal.valueOf(article.getVAT().get(0).getValue()));
            deliveryNotePosition.setVatCode(String.valueOf(article.getVAT().get(0).getIndex()));
            deliveryNotePosition.setVatRate(BigDecimal.valueOf(article.getVAT().get(0).getPercent()));
            deliveryNotePosition.setSalesReceiptId(article.getSaleReceiptID());
            deliveryNotePosition.setSalesReceiptDate(receipt.getReceiptDate());
            deliveryNotePosition.setTicketNumber( (article.getMediaID() == null || article.getMediaID().equals("")) ? null : article.getMediaID());
            deliveryNotePosition.setTotalDiscount(BigDecimal.valueOf(article.getTotalDiscount() > 0 ? article.getTotalDiscount() : 0));
            deliveryNotePosition.setType(1);
            deliveryNotePosition.setVoucherNo( (article.getMediaID() != null && article.getMediaID().equals("5")) ? article.getMediaID() : null);

            deliveryNotePositions.add(deliveryNotePosition);
            i++;
        }

        return deliveryNotePositions;
    }

    private Document extractDocument(ReceiptDto receipt) {
        int documentNo = getDocumentNumber(receipt);

        DocumentId id = new DocumentId();
        id.setServerName(serverNamePos);
        id.setDatabaseName(databaseNamePos);
        id.setDate(receipt.getReceiptDate().toLocalDate());
        id.setDocumentNo(documentNo);
        id.setCashRegisterId(receipt.getDeviceNumber());

        Document document = new Document();
        document.setId(id);
        document.setSalesReceiptId(receipt.getReceiptID());
        document.setCustomerCode(receipt.getCustomerID());
        document.setCashierId(receipt.getUser());
        document.setCountryCode("");
        document.setZipCode("");
        document.setTicketNo("");
        document.setType((short) 0);
        document.setDocumentDateTime(receipt.getReceiptDate());
        document.setSumGross(BigDecimal.valueOf(receipt.getSumGross()));
        document.setSumNet(BigDecimal.valueOf(receipt.getSumNet()));

        return document;
    }

    private List<DocumentPayments> extractDocumentPayments(ReceiptDto receipt) {
        int documentNo = getDocumentNumber(receipt);

        List<PaymentDto> payments = receipt.getPayments();
        List<DocumentPayments> documentPayments = new ArrayList<>();
        int i = 0;
        for(PaymentDto payment : payments){
            PaymentMethod paymentMethod = getPaymentMethodId(payment.getType(), payment.getSubtype());

            DocumentPaymentId id = new DocumentPaymentId();
            id.setServerName(serverNamePos);
            id.setDatabaseName(databaseNamePos);
            id.setDocumentNo(documentNo);
            id.setCashRegisterId(receipt.getDeviceNumber());
            id.setPositionNo(i+1);
            id.setDatum(receipt.getReceiptDate().toLocalDate());

            DocumentPayments documentPayment = new DocumentPayments();
            documentPayment.setId(id);
            documentPayment.setSalesReceiptId(receipt.getReceiptID());
            documentPayment.setPaymentMethodId(paymentMethod.getId());
            documentPayment.setPaymentMethodSub(paymentMethod.getSub());
            documentPayment.setAmount(BigDecimal.valueOf(payment.getValue()));
            documentPayment.setCurrencyCode("EUR");
            documentPayment.setTransactionReferenceNo(payment.getExternalReference());
            documentPayment.setVoucherNo(payment.getType() == 5 ? payment.getMediaID() : null);
            documentPayment.setProvider("Adyen");
            documentPayment.setCardNo("");
            documentPayment.setIsChange(payment.getValue() == 0 ? 1 : 0);

            documentPayments.add(documentPayment);
            i++;
        }

        return documentPayments;
    }

    private List<DocumentPositions> extractDocumentPositions(ReceiptDto receipt) {
        int documentNo = getDocumentNumber(receipt);

        List<ArticleDto> articles = receipt.getArticles();
        List<DocumentPositions> documentPositions = new ArrayList<>();
        int i = 0;
        for(ArticleDto article : articles){
            DocumentPositionId id = new DocumentPositionId();
            id.setServerName(serverNamePos);
            id.setDatabaseName(databaseNamePos);
            id.setDocumentNo(documentNo);
            id.setCashRegisterId(receipt.getDeviceNumber());
            id.setPositionNo(i+1);
            id.setDate(receipt.getReceiptDate().toLocalDate());

            DocumentPositions documentPosition = new DocumentPositions();
            documentPosition.setId(id);
            documentPosition.setSalesReceiptId(article.getSaleReceiptID());
            documentPosition.setArticleCode(article.getArticleID());
            documentPosition.setArticleGroupCode("");
            documentPosition.setUnitPrice(BigDecimal.valueOf(article.getUnitPrice()));
            documentPosition.setPrice(BigDecimal.valueOf(article.getTotalPrice()));
            documentPosition.setAmount(BigDecimal.valueOf(article.getAmount()));
            documentPosition.setVatAmount(BigDecimal.valueOf(article.getVAT().get(0).getValue()));
            documentPosition.setVatCode(String.valueOf(article.getVAT().get(0).getIndex()));
            documentPosition.setVatRate(BigDecimal.valueOf(article.getVAT().get(0).getPercent()));
            documentPosition.setDiscount(BigDecimal.valueOf(article.getTotalDiscount() > 0 ? article.getTotalDiscount() : 0));
            documentPosition.setType(0);
            documentPosition.setBarcode("");
            documentPosition.setArticleName(article.getArticleName());
            documentPosition.setVoucherNo( (article.getMediaID() != null && article.getMediaID().equals("5")) ? article.getMediaID() : null);

            documentPositions.add(documentPosition);
            i++;
        }

        return documentPositions;
    }

    private void saveReceiptCustomer(ReceiptDto receipt, String source) {
        // Find receipt customer
        Customer customer = restApiClientService.getCustomer(receipt.getCustomerID());
        if(customer != null){
            if(source.equals("webshop")) {
                CustomerLocal customerLocal = transformToLocalCustomer(customer);
                customerWebshopRepository.save(customerLocal);
            } else {
                CustomerLocal customerLocal = transformToLocalCustomer(customer);
                customerPosRepository.save(customerLocal);
            }
        }
    }

    private CustomerLocal transformToLocalCustomer(Customer customer) {
        CustomerLocal customerLocal = new CustomerLocal();
        customerLocal.setCode(String.valueOf(customer.getId()));
        customerLocal.setSalutation(customer.getSalutation());
        customerLocal.setFirstName(customer.getFirstname());
        customerLocal.setLastName(customer.getLastname());
        customerLocal.setCompany(customer.getCompany());
        customerLocal.setStreet(customer.getStreet());
        customerLocal.setZipCode(customer.getZip());
        customerLocal.setLocation(customer.getCity());
        customerLocal.setCountryCode(customer.getCountryCode());
        customerLocal.setZipCode(customer.getZip());
        customerLocal.setEMail(customer.getEmail());
        customerLocal.setDateOfBirth(customer.getBirthdate().toLocalDate());
        return customerLocal;
    }

    private PaymentMethod getPaymentMethodId(int paymentTypeId, int paymentSubtypeId) {
        Map<Integer, Integer> ravPaymentMethod = new HashMap<>();
        switch (paymentTypeId) {
            case 0:     //Cash
                return new PaymentMethod(0, 0, "Cash EUR");
            case 1:     //Invoice
                return new PaymentMethod(8, 1, "Invoice");
            //case 2:     //Bank debit
            //    break;
            case 3:     //EC Cash
                return new PaymentMethod(1, 11, "EC debit charge");
            //case 4:     //Home loan
            //    break;
            case 5:     //Value card (Voucher?)
                return new PaymentMethod(7, 0, "payment voucher");
            case 6:     //Credit card/other
                switch (paymentSubtypeId) {
                    case 1:     //Visa
                        return new PaymentMethod(1, 5, "VISA");
                    case 2:     //Mastercard
                        return new PaymentMethod(1, 6, "Mastercard");
                    case 3:     //American Express
                        return new PaymentMethod(1, 8, "American Express");
                    case 4:     //Diners
                        return new PaymentMethod(1, 7, "Diners");
                    case 5:     //Paypal (Web)
                        return new PaymentMethod(1, 20,"PayPal");
                    case 6:     //Kreditkarte online (Web)
                        return new PaymentMethod(1, 21, "Adyen");
                    case 99:    //Paypal
                        return new PaymentMethod(1, 20, "PayPal");
                    default:
                        return new PaymentMethod(8, 1, "Unknown");
                }
            default:
                return new PaymentMethod(8, 1, "Unknown");
        }
    }

    private int getDocumentNumber(ReceiptDto receipt) {
        int receiptNumber = receipt.getReceiptNumber();
        int shiftNumber = receipt.getShiftNumber();
        return shiftNumberMultiplier * shiftNumber + receiptNumber;
    }
}
