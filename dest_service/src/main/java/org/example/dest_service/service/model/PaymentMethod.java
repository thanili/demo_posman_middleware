package org.example.dest_service.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentMethod {
    private int id;
    private int sub;
    private String description;
}
