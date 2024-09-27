package org.example.dest_service.repository.pos;

import org.example.dest_service.entity.common.CustomerLocal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerPosRepository extends JpaRepository<CustomerLocal, String> {
    Optional<CustomerLocal> findByCode(String code);
}