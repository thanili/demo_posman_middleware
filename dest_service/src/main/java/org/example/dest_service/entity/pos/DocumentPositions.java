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
@Table(name = "\"DocumentPositions\"")
public class DocumentPositions {
    @EmbeddedId
    private DocumentPositionId id;

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

    @Column(name = "UnitPrice", precision = 20, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "Price", precision = 20, scale = 2)
    private BigDecimal price;

    @Column(name = "Discount", precision = 20, scale = 2)
    private BigDecimal discount;

    @Column(name = "VATCode", nullable = false, length = 20)
    private String vatCode;

    @Column(name = "VATRate", nullable = false, precision = 5, scale = 2)
    private BigDecimal vatRate;

    @Column(name = "VATAmount", precision = 20, scale = 2)
    private BigDecimal vatAmount;

    @Column(name = "Barcode", length = 40)
    private String barcode;

    @Column(name = "ArticleName")
    private String articleName;

    @Column(name = "SalesReceiptId", length = 255)
    private String salesReceiptId;
}
