package org.example.dest_service.entity.pos;

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
@Table(name = "\"DocumentPayments\"")
public class DocumentPayments {
    @EmbeddedId
    private DocumentPaymentId id;

    @Column(name = "IsChange", nullable = false)
    private Integer isChange;

    @Column(name = "PaymentMethodId", nullable = false)
    private Integer paymentMethodId;

    @Column(name = "PaymentMethodSub", nullable = false)
    private Integer paymentMethodSub;

    @Column(name = "CurrencyCode", nullable = false, length = 10)
    private String currencyCode;

    @Column(name = "Amount", precision = 20, scale = 2)
    private BigDecimal amount;

    @Column(name = "CardNo", length = 40)
    private String cardNo;

    @Column(name = "VoucherNo", length = 40)
    private String voucherNo;

    @Column(name = "Provider", length = 20)
    private String provider;

    @Column(name = "TransactionReferenceNo", length = 50)
    private String transactionReferenceNo;

    @Column(name = "SalesReceiptId", length = 255)
    private String salesReceiptId;
}
