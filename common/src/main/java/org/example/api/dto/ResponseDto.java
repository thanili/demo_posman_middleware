package org.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseDto {
    @JsonProperty(value = "Result",required = true)
    private int result;
    @JsonProperty("Error")
    private String error;
}
