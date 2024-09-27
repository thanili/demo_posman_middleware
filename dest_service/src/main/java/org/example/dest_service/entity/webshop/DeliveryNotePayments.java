package org.example.dest_service.entity.webshop;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@ToString
@Table(name = "\"DeliveryNotePayments\"")
public class DeliveryNotePayments {
    @EmbeddedId
    private DeliveryNotePaymentId id;

    @Column(name = "PaymentMethodId", nullable = false)
    private Integer paymentMethodId;

    @Column(name = "PaymentMethodSub", nullable = false)
    private Integer paymentMethodSub;

    @Column(name = "CurrencyCode", nullable = false, length = 10)
    private String currencyCode;

    @Column(name = "Amount", nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(name = "TransactionNo", length = 40)
    private String transactionNo;

    @Column(name = "VoucherNo", length = 40)
    private String voucherNo;

    @Column(name = "SalesReceiptId", length = 255)
    private String salesReceiptId;

}
