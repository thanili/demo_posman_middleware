package org.example.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserDto {
    @JsonProperty("Company")
    private final String company;
    @JsonProperty("ID")
    private final int id;
    @JsonProperty("Name")
    private final String name;
}
