package org.example.dest_service.entity.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dest_service.entity.common.CustomerLocal;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link CustomerLocal}
 */
@Getter
@Setter
@ToString
public class CustomerLocalDto implements Serializable {
    String code;
    String company;
    String salutation;
    String firstName;
    String lastName;
    String street;
    String countryCode;
    String zipCode;
    String location;
    LocalDate dateOfBirth;
    String eMail;
}