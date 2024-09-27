package org.example.dest_service.entity.pos.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dest_service.entity.pos.DocumentPaymentId;
import org.example.dest_service.entity.pos.DocumentPayments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link DocumentPayments}
 */
@Getter
@Setter
@ToString
public class DocumentPaymentsDto implements Serializable {
    DocumentPaymentIdDto id;
    Integer isChange;
    Integer paymentMethodId;
    Integer paymentMethodSub;
    String currencyCode;
    BigDecimal amount;
    String cardNo;
    String voucherNo;
    String provider;
    String transactionReferenceNo;
    String salesReceiptId;

    /**
     * DTO for {@link DocumentPaymentId}
     */
    @Getter
    @Setter
    @ToString
    public static class DocumentPaymentIdDto implements Serializable {
        String serverName;
        String databaseName;
        Integer cashRegisterId;
        LocalDate datum;
        Integer documentNo;
        Integer positionNo;
    }
}