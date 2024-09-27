package org.example.source_api.utils;

import org.example.source_api.entity.user.ApiUser;
import org.example.source_api.repository.user.ApiUserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class AccessTokenHelper {
    private final Logger logger;
    private final ApiUserRepository apiUserRepository;

    @Value("${net.mspos.possible.service.apikey.valid.period}")
    private String validityPeriod;

    public AccessTokenHelper(Logger logger, ApiUserRepository apiUserRepository) {
        this.logger = logger;
        this.apiUserRepository = apiUserRepository;
    }

    /**
     * Updates the access token for the given API user.
     * If the current access token is null or invalid, a new access token is generated,
     * and the creation time is updated.
     *
     * @param apiUser the API user whose access token needs to be updated
     */
    public void updateAccessToken(ApiUser apiUser) {
        String accessToken = apiUser.getAccessToken();
        OffsetDateTime createdAt = apiUser.getCreatedAt();
        if (accessToken == null || !isAccessTokenValid(createdAt.toLocalDateTime())) {
            accessToken = UUID.randomUUID().toString();
            OffsetDateTime currentOffsetDateTime = OffsetDateTime.now();
            apiUser.setAccessToken(accessToken);
            apiUser.setCreatedAt(currentOffsetDateTime);
            apiUserRepository.save(apiUser);
        }
    }

    /**
     * Checks if the access token created at a given time is still valid based on the predefined validity period.
     *
     * @param createdAt the LocalDateTime when the access token was created
     * @return true if the access token is still valid, false otherwise
     */
    public boolean isAccessTokenValid(LocalDateTime createdAt) {
        if (createdAt == null) {
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now(ZoneOffset.UTC);
        long minutesElapsed = ChronoUnit.MINUTES.between(createdAt.atZone(ZoneOffset.UTC).toLocalDateTime(), currentTime);
        logger.info("CreatedAt: " + createdAt.atZone(ZoneOffset.UTC).toLocalDateTime());
        logger.info("Current time: " + currentTime);
        try {
            int validityMinutes = Integer.parseInt(validityPeriod);
            logger.info("Validity period: " + validityMinutes + " minutes");
            logger.info("Minutes elapsed: " + minutesElapsed + " minutes");
            return minutesElapsed < validityMinutes;
        } catch (NumberFormatException e) {
            logger.error("Invalid validity period format: " + validityPeriod, e);
            return false;
        }
    }
}
