package org.example.dest_service.entity.pos;

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
@Table(name = "\"Document\"")
public class Document {
    @EmbeddedId
    private DocumentId id;

    @Column(name = "SalesReceiptId", nullable = false, length = 255)
    private String SalesReceiptId;

    @Column(name = "CashierId", nullable = false, length = 20)
    private String cashierId;

    @Column(name = "CountryCode", length = 10)
    private String countryCode;

    @Column(name = "CustomerCode", length = 40)
    private String customerCode;

    @Column(name = "ZipCode", length = 10)
    private String zipCode;

    @Column(name = "TicketNo", length = 40)
    private String ticketNo;

    @Column(name = "DocumentDateTime", nullable = false)
    private LocalDateTime documentDateTime;

    @Column(name = "Type", nullable = false)
    private Short type;

    @Column(name = "SumGross", precision = 20, scale = 2)
    private BigDecimal sumGross;

    @Column(name = "SumNet", precision = 20, scale = 2)
    private BigDecimal sumNet;
}
