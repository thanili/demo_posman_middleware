package org.example.source_api.entity.transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "\"Receipt\"")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    //@Column(name = "receiptJson", columnDefinition = "NVARCHAR(MAX)", nullable = false) //MSSQL
    @Column(name = "receiptJson", columnDefinition = "LONGTEXT", nullable = false) //MariaDB
    private String receiptJson;

}
