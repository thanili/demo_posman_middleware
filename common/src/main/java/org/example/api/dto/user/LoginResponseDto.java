package org.example.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LoginResponseDto {
    @JsonProperty("ID")
    private final String id;
    @JsonProperty("Timestamp")
    private final String timestamp;
    @JsonProperty("ChannelID")
    private final Integer channelId;
    @JsonProperty("User")
    private final UserDto user;
}
