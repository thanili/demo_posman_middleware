package org.example.dest_service.entity.webshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dest_service.entity.common.dto.CustomerLocalDto;
import org.example.dest_service.entity.webshop.DeliveryNote;
import org.example.dest_service.entity.webshop.DeliveryNoteId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link DeliveryNote}
 */
@Getter
@Setter
@ToString
public class DeliveryNoteDto implements Serializable {
    DeliveryNoteIdDto id;
    String customerCode;
    BigDecimal sumGross;
    BigDecimal sumNet;
    Integer externalReference;
    String customerId;
    String salesReceiptId;
    String deviceNumber;
    Integer year;
    String shiftNumber;
    Integer receiptNumber;
    @JsonProperty("articles")
    List<DeliveryNotePositionsDto> deliveryNotePositionsDtos;
    @JsonProperty("payments")
    List<DeliveryNotePaymentsDto> deliveryNotePaymentsDtos;
    @JsonProperty("customer")
    CustomerLocalDto customerLocalDto;

    /**
     * DTO for {@link DeliveryNoteId}
     */
    @Getter
    @Setter
    @ToString
    public static class DeliveryNoteIdDto implements Serializable {
        String serverName;
        String databaseName;
        LocalDate date;
        Integer documentNo;
    }
}