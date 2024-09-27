package org.example.api.dto.receipt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto {
    /*
    VK = Sale / ST = Cancellation / HKA = Home loan settlement
    Type: String
    Optional: No
     */
    @JsonProperty(value = "BookingCategory",required = true)
    private String bookingCategory;
    /*
    HKS article ID
    Type: String
    Optional: No
     */
    @JsonProperty(value = "ArticleID",required = true)
    private String articleID;
    /*
    Article Name
    Type: String
    Optional: No
     */
    @JsonProperty(value = "ArticleName",required = true)
    private String articleName;
    /*
    Sales rule of the item: 1050: product / 1060: service on ticket / 1080: prepaid card or voucher
    Type: Int16
    Optional: No
     */
    @JsonProperty(value = "SellingRule",required = true)
    private int sellingRule;
    /*
    Usually identical to the item name
    Type: String
    Optional: No
     */
    @JsonProperty(value = "BookingText",required = true)
    private String bookingText;
    /*
    Amount
    Type: Int32
    Optional: No
     */
    @JsonProperty(value = "Amount",required = true)
    private int amount;
    /*
    Unit Price
    Type: Float
    Optional: No
     */
    @JsonProperty(value = "UnitPrice",required = true)
    private double unitPrice;
    /*
    Total price
    Type: Float
    Optional: No
     */
    @JsonProperty(value = "TotalPrice",required = true)
    private double totalPrice;
    /*
    Discount granted
    Type: Float
    Optional: Yes
     */
    @JsonProperty("TotalDiscount")
    private double totalDiscount;
    /*
    Article ID of the external system (for article import)
    Type: String
    Optional: Yes
     */
    @JsonProperty("ExternalArticleID")
    private String externalArticleId;
    /*
    Only when purchasing tickets/vouchers: media number
    Type: Int32
    Optional: Yes
     */
    @JsonProperty("MediaNumber")
    private int mediaNumber;
    /*
    Only when purchasing tickets/vouchers: circulation
    Type: Int32
    Optional: Yes
     */
    @JsonProperty("MediaCycleNumber")
    private int mediaCycleNumber;
    /*
    Only when purchasing a ticket/voucher: barcode number or RFID serial number
    Type: String
    Optional: Yes
     */
    @JsonProperty("MediaID")
    private String mediaID;

    /*
    Proof of the original sale
    Type: String
    Optional: Yes
     */
    @JsonProperty("SaleReceiptID")
    private String saleReceiptID;
    /*
    Time of original sale
    Type: DateTime
    Optional: Yes
     */
    @JsonProperty("SaleReceiptDate")
    private LocalDateTime saleReceiptDate;

    /*
    VAT breakdown of the booking / VAT value of “real” payments (no VAT is incurred for payments with a home loan)
    Type: Array(VAT)
    Optional: No
     */
    @JsonProperty(value = "VAT",required = true)
    private List<VATDto> vAT;
}
