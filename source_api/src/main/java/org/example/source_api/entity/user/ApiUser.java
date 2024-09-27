package org.example.source_api.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@ToString
@Table(name = "\"Apiuser\"")
public class ApiUser {
    @EmbeddedId
    private ApiUserId id;

    @Column(name = "api_key", nullable = false)
    private String apiKey;
    @Column(name = "access_token", nullable = true)
    private String accessToken;
    @Column(name = "created_at", nullable = true)
    private OffsetDateTime createdAt;
}
