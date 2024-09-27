package org.example.api.dto.receipt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VATDto {
    /*
    VAT index according to HKS
    Type: Int16
    Optional: No
     */
    @JsonProperty(value = "Index",required = true)
    private int index;
    /*
    VAT rate
    Type: Float
    Optional: No
     */
    @JsonProperty(value = "Percent",required = true)
    private double percent;
    /*
    VAT value
    Type: Float
    Optional: No
     */
    @JsonProperty(value = "Value",required = true)
    private double value;
}
