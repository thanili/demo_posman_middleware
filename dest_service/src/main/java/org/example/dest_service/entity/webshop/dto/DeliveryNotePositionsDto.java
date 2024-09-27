package org.example.dest_service.entity.webshop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dest_service.entity.webshop.DeliveryNotePositionId;
import org.example.dest_service.entity.webshop.DeliveryNotePositions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link DeliveryNotePositions}
 */
@Getter
@Setter
@ToString
public class DeliveryNotePositionsDto implements Serializable {
    DeliveryNotePositionIdDto id;
    Integer type;
    String voucherNo;
    String articleCode;
    String articleGroupCode;
    BigDecimal amount;
    BigDecimal price;
    String vatCode;
    BigDecimal vatRate;
    BigDecimal vatAmount;
    String ticketNumber;
    String articleId;
    BigDecimal unitPrice;
    BigDecimal totalDiscount;
    String salesReceiptId;
    LocalDateTime salesReceiptDate;

    /**
     * DTO for {@link DeliveryNotePositionId}
     */
    @Getter
    @Setter
    @ToString
    public static class DeliveryNotePositionIdDto implements Serializable {
        String serverName;
        String databaseName;
        LocalDate date;
        Integer documentNo;
        Integer positionNo;
    }
}