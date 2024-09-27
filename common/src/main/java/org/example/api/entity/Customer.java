package org.example.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "countryCode", nullable = false)
    private String countryCode;
    @Column(name = "salutation")
    private String salutation;
    @Column(name = "gender")
    private String gender;
    @Column(name = "title")
    private String title;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "birthdate")
    private LocalDateTime birthdate;
    @Column(name = "company")
    private String company;
    @Column(name = "street")
    private String street;
    @Column(name = "zip")
    private String zip;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "locked")
    private boolean locked;

}
