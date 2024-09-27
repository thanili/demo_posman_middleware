package org.example.source_api.api;

import org.example.api.dto.user.LoginResponseDto;
import org.example.api.dto.user.UserDto;
import org.example.source_api.entity.user.ApiUser;
import org.example.source_api.repository.user.ApiUserRepository;
import org.example.source_api.utils.AccessTokenHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * UserController is a REST controller that handles user login operations.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final Logger logger;
    private final ApiUserRepository apiUserRepository;
    private final AccessTokenHelper accessTokenHelper;

    @Value("${net.mspos.possible.service.apikey.valid.period}")
    private String validityPeriod;

    @Autowired
    public UserController(ApiUserRepository apiUserRepository, Logger logger, AccessTokenHelper accessTokenHelper) {
        this.apiUserRepository = apiUserRepository;
        this.logger = logger;
        this.accessTokenHelper = accessTokenHelper;
    }

    /**
     * Handles the login process for API users by validating the provided secret key.
     * If the secret key is valid, returns a response containing the access token and user details.
     *
     * @param secretKey the secret key provided by the user for authentication
     * @return a ResponseEntity containing a LoginResponseDto with the access token, timestamp, channel ID, and user details;
     *         or an UNAUTHORIZED status if the secret key is invalid.
     */
    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestParam("ASecretKey") String secretKey) {
        // Guard clause to find ApiUser with secretKey
        Optional<ApiUser> apiUserOptional = apiUserRepository.findByApiKey(secretKey);
        if (!apiUserOptional.isPresent()) {
            logger.info("UserController login: API user not found in local registry");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ApiUser apiUser = apiUserOptional.get();
        logger.info("UserController login: API user found in local registry: " +
                apiUser.getId().getCompany() + ":" + apiUser.getId().getUsername());

        accessTokenHelper.updateAccessToken(apiUser);
        String timestamp = formatTimestamp(apiUser.getCreatedAt());

        // Create response
        UserDto userDto = new UserDto(apiUser.getId().getCompany(), apiUser.getId().getId(), apiUser.getId().getUsername());
        LoginResponseDto response = new LoginResponseDto(apiUser.getAccessToken(), timestamp,123,userDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Formats the provided OffsetDateTime into a standardized string representation.
     *
     * @param createdAt the OffsetDateTime object to format
     * @return a formatted string in the "yyyy-MM-dd'T'HH:mm:ss'Z'" pattern
     */
    private String formatTimestamp(OffsetDateTime createdAt) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return createdAt.truncatedTo(ChronoUnit.SECONDS).format(formatter);
    }

}
