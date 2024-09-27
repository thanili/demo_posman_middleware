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
@Table(name = "\"DeliveryNote\"")
public class DeliveryNote {
    @EmbeddedId
    private DeliveryNoteId id;

    @Column(name = "CustomerCode", length = 40)
    private String customerCode;

    @Column(name = "SumGross", nullable = false, precision = 8, scale = 2)
    private BigDecimal sumGross;

    @Column(name = "SumNet", nullable = false, precision = 8, scale = 2)
    private BigDecimal sumNet;

    @Column(name = "ExternalReference")
    private Integer externalReference;

    @Column(name = "CustomerId", length = 40)
    private String customerId;

    @Column(name = "SalesReceiptId")
    private String salesReceiptId;

    @Column(name = "DeviceNumber", length = 40)
    private String deviceNumber;

    @Column(name = "\"Year\"")
    private Integer year;

    @Column(name = "ShiftNumber", length = 40)
    private String shiftNumber;

    @Column(name = "ReceiptNumber")
    private Integer receiptNumber;
}
