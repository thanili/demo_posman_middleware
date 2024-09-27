package org.example.dest_service.entity.pos.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dest_service.entity.pos.DocumentPositionId;
import org.example.dest_service.entity.pos.DocumentPositions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link DocumentPositions}
 */
@Getter
@Setter
@ToString
public class DocumentPositionsDto implements Serializable {
    DocumentPositionIdDto id;
    Integer type;
    String voucherNo;
    String articleCode;
    String articleGroupCode;
    BigDecimal amount;
    BigDecimal unitPrice;
    BigDecimal price;
    BigDecimal discount;
    String vatCode;
    BigDecimal vatRate;
    BigDecimal vatAmount;
    String barcode;
    String articleName;
    String salesReceiptId;

    /**
     * DTO for {@link DocumentPositionId}
     */
    @Getter
    @Setter
    @ToString
    public static class DocumentPositionIdDto implements Serializable {
        String serverName;
        String databaseName;
        Integer cashRegisterId;
        LocalDate date;
        Integer documentNo;
        Integer positionNo;
    }
}