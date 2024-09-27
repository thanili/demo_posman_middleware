package org.example.api.dto.receipt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PaymentDto {
    /*
    Z = payment (e.g. cash / EC / prepaid card) / HKZ = house loan payment
    Type: String
    Optional: No
     */
    @JsonProperty(value = "Category",required = true)
    private String category;
    /*
    e.g. 0= cash, ..., 6 = credit card / other - see HKS payment methods
    Type: Int16
    Optional: No
     */
    @JsonProperty(value = "Type",required = true)
    private int type;
    /*
    (only for payment method 6: e.g. 1 = Visa, 2 = MasterCard, ...) - see HKS payment methods
    Type: Int16
    Optional: No
     */
    @JsonProperty(value = "SubType")
    private int subtype;
    /*
    Amount of payment
    Type: Float
    Optional: No
     */
    @JsonProperty(value = "Value",required = true)
    private double value;
    /*
    External reference of payment (if available)
    Type: String
    Optional: Yes
     */
    @JsonProperty("ExternalReference")
    private String externalReference;

    /*
    Only for voucher/value card payment: item of the voucher
    Type: String
    Optional: Yes
     */
    @JsonProperty("ArticleID")
    private String articleID;
    /*
    Only for voucher/value card payment: item of the voucher
    Type: String
    Optional: Yes
     */
    @JsonProperty("ArticleName")
    private String articleName;
    /*
    Only for voucher/prepaid card payment: Article ID of the external system
    Type: String
    Optional: Yes
     */
    @JsonProperty("ExternalArticleId")
    private String externalArticleId;
    /*
    Only for voucher/prepaid card payment: media number of the voucher
    Type: Int32
    Optional: Yes
     */
    @JsonProperty("MediaNumber")
    private Integer mediaNumber;
    /*
    Only for voucher/prepaid card payment: media number of the voucher
    Type: Int32
    Optional: Yes
     */
    @JsonProperty("MediaCycleNumber")
    private Integer mediaCycleNumber;
    /*
    Only for voucher/value card payment: ID of the voucher
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
}
