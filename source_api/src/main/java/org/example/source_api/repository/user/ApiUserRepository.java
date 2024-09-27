package org.example.source_api.repository.user;

import org.example.source_api.entity.user.ApiUser;
import org.example.source_api.entity.user.ApiUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUserRepository extends JpaRepository<ApiUser, ApiUserId> {
  Optional<ApiUser> findByApiKey(String apiKey);
  Optional<ApiUser> findByAccessToken(String accessToken);
  Optional<ApiUser> findByIdUsername(String apiUser);
}