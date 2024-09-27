package org.example.dest_service.entity.pos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dest_service.entity.common.dto.CustomerLocalDto;
import org.example.dest_service.entity.pos.Document;
import org.example.dest_service.entity.pos.DocumentId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Document}
 */
@Getter
@Setter
@ToString
public class DocumentDto implements Serializable {
    DocumentIdDto id;
    String SalesReceiptId;
    String cashierId;
    String countryCode;
    String customerCode;
    String zipCode;
    String ticketNo;
    LocalDateTime documentDateTime;
    Short type;
    BigDecimal sumGross;
    BigDecimal sumNet;
    @JsonProperty("articles")
    List<DocumentPositionsDto> documentPositionsDtos;
    @JsonProperty("payments")
    List<DocumentPaymentsDto> documentPaymentsDtos;
    @JsonProperty("customer")
    CustomerLocalDto customerLocalDto;

    /**
     * DTO for {@link DocumentId}
     */
    @Getter
    @Setter
    @ToString
    public static class DocumentIdDto implements Serializable {
        String serverName;
        String databaseName;
        Integer cashRegisterId;
        LocalDate date;
        Integer documentNo;
    }
}