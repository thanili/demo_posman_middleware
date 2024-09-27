package org.example.api.dto.receipt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ReceiptDto {
    /*
    Guid of the receipt
    Type: UUID
    Optional: No
     */
    @JsonProperty(value = "ReceiptID",required = true)
    private String receiptID;
    /*
    Device number of the document
    Type: Int32
    Optional: No
     */
    @JsonProperty(value = "DeviceNumber",required = true)
    private int deviceNumber;
    /*
    Year of receipt
    Type: Int32
    Optional: No
     */
    @JsonProperty(value = "Year",required = true)
    private int year;
    /*
    Shift number of the document
    Type: Int32
    Optional: No
     */
    @JsonProperty(value = "ShiftNumber",required = true)
    private int shiftNumber;
    /*
    Consecutive number within the shift
    Type: Int32
    Optional: No
     */
    @JsonProperty(value = "ReceiptNumber",required = true)
    private int receiptNumber;
    /*
    Time of creation
    Type: DateTime
    Optional: No
     */
    @JsonProperty(value = "ReceiptDate", required = true)
    private LocalDateTime receiptDate;
    /*
    Cashier name
    Type: String
    Optional: No
     */
    @JsonProperty(value = "User", required = true)
    private String user;
    /*
    Cashier number
    Type: Int32
    Optional: No
     */
    @JsonProperty(value = "UserID",required = true)
    private int userID;
    /*
    Customer ID of the document customer
    Format: [operator number].[system number].[number]
    Type: String
    Optional: Yes
     */
    @JsonProperty("CustomerID")
    private String customerID;
    /*
    Total payments / gross
    Type: Float
    Optional: No
     */
    @JsonProperty(value = "SumGross",required = true)
    private double sumGross;
    /*
    Total payments / net
    Type: Float
    Optional: No
     */
    @JsonProperty(value = "SumNet",required = true)
    private double sumNet;
    /*
    Order number of the web shop or catering system
    Type: String ??? in the payload it is int
    Optional: Yes
     */
    @JsonProperty("ExternalReference")
    private int externalReference;
    /*
    List of bookings/articles
    Type: Array(Article)
    Optional: No
     */
    @JsonProperty(value = "Articles",required = true)
    private List<ArticleDto> articles;
    /*
    List of payments
    Type: Array(Payment)
    Optional: No
     */
    @JsonProperty(value = "Payments",required = true)
    private List<PaymentDto> payments;
}
