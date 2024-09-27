package org.example.dest_service.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "\"Customer\"")
public class CustomerLocal {
    @Id
    @Column(name = "Code", nullable = false, length = 40)
    private String code;

    //Company
    @Column(name = "Company", length = 40)
    private String company;

    //Salutation
    @Column(name = "Salutation", length = 40)
    private String salutation;

    //First name
    @Column(name = "FirstName", length = 40)
    private String firstName;

    //Last name
    @Column(name = "LastName", length = 40)
    private String lastName;

    //Street
    @Column(name = "Street")
    private String street;

    //Country code
    @Column(name = "CountryCode", length = 10)
    private String countryCode;

    //Postcode
    @Column(name = "ZipCode", length = 10)
    private String zipCode;

    //Location
    @Column(name = "Location")
    private String location;

    //Date of birth
    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "EMail")
    private String eMail;
}
