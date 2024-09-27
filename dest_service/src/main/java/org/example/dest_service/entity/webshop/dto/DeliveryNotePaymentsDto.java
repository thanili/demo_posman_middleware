package org.example.dest_service.entity.webshop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dest_service.entity.webshop.DeliveryNotePaymentId;
import org.example.dest_service.entity.webshop.DeliveryNotePayments;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link DeliveryNotePayments}
 */
@Getter
@Setter
@ToString
public class DeliveryNotePaymentsDto implements Serializable {
    DeliveryNotePaymentIdDto id;
    Integer paymentMethodId;
    Integer paymentMethodSub;
    String currencyCode;
    BigDecimal amount;
    String transactionNo;
    String voucherNo;
    String salesReceiptId;

    /**
     * DTO for {@link DeliveryNotePaymentId}
     */
    @Getter
    @Setter
    @ToString
    public static class DeliveryNotePaymentIdDto implements Serializable {
        String serverName;
        String databaseName;
        LocalDate date;
        Integer documentNo;
        Integer positionNo;
    }
}