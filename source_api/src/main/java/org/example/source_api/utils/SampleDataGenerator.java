package org.example.source_api.utils;

import org.example.api.dto.receipt.ArticleDto;
import org.example.api.dto.receipt.PaymentDto;
import org.example.api.dto.receipt.ReceiptDto;
import org.example.api.dto.receipt.VATDto;
import org.example.api.entity.Customer;
import org.example.source_api.entity.user.ApiUser;
import org.example.source_api.entity.user.ApiUserId;
import org.example.source_api.repository.transaction.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SampleDataGenerator {
    @Autowired
    CommonFunctions commonFunctions;
    @Autowired
    CustomerRepository customerRepository;

    public List<ReceiptDto> generateReceipts(int size) {
        List<ReceiptDto> receiptDtos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            receiptDtos.add(generateReceiptDto());
        }
        return receiptDtos;
    }

    public List<Customer> generateCustomers(int size) {
        List<Customer> customers = new ArrayList<>();
        for(int i=0; i<size; i++) {
            customers.add(generateCustomer());
        }
        return customers;
    }

    public ApiUser createApiUser() {
        return generateApiUser();
    }

    private ReceiptDto generateReceiptDto() {

        List<Customer> customers = customerRepository.findAll();
        Customer customer = customers.get(commonFunctions.generateRandomInt(0, customers.size() - 1));

        String receiptId = java.util.UUID.randomUUID().toString();
        LocalDateTime receiptDate = LocalDateTime.now().minusSeconds(commonFunctions.generateRandomLong(0L, 1000000000L));

        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setReceiptID(receiptId);
        receiptDto.setDeviceNumber(commonFunctions.selectRandomInt(81010001,81010002));
        receiptDto.setYear(LocalDateTime.now().getYear());
        receiptDto.setShiftNumber(commonFunctions.generateRandomInt(0,100));
        receiptDto.setReceiptNumber(commonFunctions.generateRandomInt(0,1000000));
        receiptDto.setReceiptDate(receiptDate);
        receiptDto.setUser("System");
        receiptDto.setUserID(2000);
        receiptDto.setCustomerID(String.valueOf(customer.getId()));
        receiptDto.setSumGross(commonFunctions.generateRandomDouble(1,100));
        receiptDto.setSumNet(commonFunctions.generateRandomDouble(1,100));
        receiptDto.setExternalReference(commonFunctions.generateRandomInt(1000000,9999999));

        List<ArticleDto> articleDtos = new ArrayList<>();
        int listSize = commonFunctions.generateRandomInt(1,4);
        for(int i=0; i<listSize; i++) {
            ArticleDto articleDto = new ArticleDto();
            articleDto.setBookingCategory("XX");
            articleDto.setArticleID(java.util.UUID.randomUUID().toString());
            articleDto.setArticleName("Test Article - " + commonFunctions.generateRandomInt(100, 10000));
            articleDto.setSellingRule(1000);
            articleDto.setBookingText(articleDto.getArticleName());
            articleDto.setAmount(commonFunctions.generateRandomInt(1, 5));
            articleDto.setUnitPrice(commonFunctions.generateRandomDouble(1, 100));
            articleDto.setTotalPrice(articleDto.getAmount() * articleDto.getUnitPrice());
            articleDto.setTotalDiscount(commonFunctions.generateRandomDouble(0, (int) articleDto.getTotalPrice()));
            articleDto.setExternalArticleId(String.valueOf(commonFunctions.generateRandomInt(1000, 9999999)));
            articleDto.setMediaNumber(commonFunctions.generateRandomInt(10000, 999999999));
            articleDto.setMediaCycleNumber(1);
            articleDto.setMediaID(String.valueOf(commonFunctions.generateRandomInt(1000000, 1000000000)));
            articleDto.setSaleReceiptID(receiptId);
            articleDto.setSaleReceiptDate(receiptDate);

            List<VATDto> vatDtos = new ArrayList<>();
            int vatListSize = commonFunctions.generateRandomInt(1,3);
            for(int j=0; j<vatListSize; j++) {
                VATDto vatDto = new VATDto();
                vatDto.setIndex(j);
                vatDto.setPercent(commonFunctions.generateRandomDouble(0,20));
                vatDto.setValue(commonFunctions.generateRandomDouble(0,100));
                vatDtos.add(vatDto);
            }

            articleDto.setVAT(vatDtos);
            articleDtos.add(articleDto);
        }
        receiptDto.setArticles(articleDtos);

        List<PaymentDto> paymentDtos = new ArrayList<>();
        int paymentsListSize = commonFunctions.generateRandomInt(1,3);
        for(int i=0; i<paymentsListSize; i++) {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setSaleReceiptID(receiptId);
            paymentDto.setSaleReceiptDate(receiptDate);
            paymentDto.setArticleID(null);
            paymentDto.setArticleName(null);
            paymentDto.setExternalReference(null);
            paymentDto.setExternalArticleId(null);
            paymentDto.setValue(commonFunctions.generateRandomDouble(0,1000));
            paymentDto.setCategory("z");
            paymentDto.setMediaCycleNumber(null);
            paymentDto.setMediaID(null);
            paymentDto.setMediaNumber(null);
            paymentDto.setType(commonFunctions.generateRandomInt(0,6));
            paymentDto.setSubtype(commonFunctions.generateRandomInt(1,100));

            paymentDtos.add(paymentDto);
        }
        receiptDto.setPayments(paymentDtos);

        return receiptDto;
    }

    private Customer generateCustomer() {
        String country = commonFunctions.generateRandomCountry();

        Customer customer = new Customer();
        customer.setSalutation(commonFunctions.generateRandomSalutation());
        customer.setFirstname(commonFunctions.generateRandomFirstName());
        customer.setLastname(commonFunctions.generateRandomLastName());
        customer.setCountry(country);
        customer.setCountryCode(commonFunctions.getCountryCode(country));
        customer.setCity(commonFunctions.generateRandomCity());
        customer.setStreet(commonFunctions.generateRandomStreetAddress());
        customer.setZip(commonFunctions.generateRandomZipCode());
        customer.setCompany(commonFunctions.generateRandomCompanyName());
        customer.setTitle(commonFunctions.generateRandomJobTitle());
        customer.setMobile(commonFunctions.generateRandomPhoneNumber());
        customer.setPhone(commonFunctions.generateRandomPhoneNumber());
        customer.setEmail(commonFunctions.generateRandomEmail());
        customer.setBirthdate(commonFunctions.generateRandomBirthDate());
        customer.setGender(commonFunctions.generateRandomGender());
        customer.setLocked(false);

        return customer;
    }

    private ApiUser generateApiUser() {
        ApiUserId id = new ApiUserId();
        id.setId(1);
        id.setUsername("apiUser");
        id.setCompany("GrowIT");

        ApiUser apiUser = new ApiUser();
        apiUser.setId(id);
        apiUser.setApiKey("t531ght598");

        return apiUser;
    }
}
