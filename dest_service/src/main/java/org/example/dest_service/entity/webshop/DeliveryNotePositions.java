package org.example.dest_service.entity.webshop;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@Table(name = "\"DeliveryNotePositions\"")
public class DeliveryNotePositions {
    @EmbeddedId
    private DeliveryNotePositionId id;

    @Column(name = "Type", nullable = false)
    private Integer type;

    @Column(name = "VoucherNo", length = 40)
    private String voucherNo;

    @Column(name = "ArticleCode", nullable = false, length = 255)
    private String articleCode;

    @Column(name = "ArticleGroupCode", nullable = false, length = 20)
    private String articleGroupCode;

    @Column(name = "Amount", nullable = false, precision = 20, scale = 4)
    private BigDecimal amount;

    @Column(name = "Price", nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(name = "VATCode", nullable = false, length = 20)
    private String vatCode;

    @Column(name = "VATRate", nullable = false, precision = 5, scale = 2)
    private BigDecimal vatRate;

    @Column(name = "VATAmount", nullable = false, precision = 8, scale = 2)
    private BigDecimal vatAmount;

    @Column(name = "TicketNumber", length = 40)
    private String ticketNumber;

    @Column(name = "ArticleId", length = 250)
    private String articleId;

    @Column(name = "UnitPrice", precision = 8, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "TotalDiscount", precision = 8, scale = 2)
    private BigDecimal totalDiscount;

    @Column(name = "SalesReceiptId", length = 255)
    private String salesReceiptId;

    @Column(name = "SalesReceiptDate")
    private LocalDateTime salesReceiptDate;
}
