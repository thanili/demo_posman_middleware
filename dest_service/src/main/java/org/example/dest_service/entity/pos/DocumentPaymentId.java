package org.example.dest_service.entity.pos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@Embeddable
public class DocumentPaymentId implements Serializable {
    private static final long serialVersionUID = -1157682684739082488L;

    @Column(name = "ServerName", nullable = false, length = 128)
    private String serverName;

    @Column(name = "DatabaseName", nullable = false, length = 128)
    private String databaseName;

    @Column(name = "CashRegisterId", nullable = false)
    private Integer cashRegisterId;

    @Column(name = "Date", nullable = false)
    private LocalDate datum;

    @Column(name = "DocumentNo", nullable = false)
    private Integer documentNo;

    @Column(name = "PositionNo", nullable = false)
    private Integer positionNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DocumentPaymentId entity = (DocumentPaymentId) o;
        return Objects.equals(this.datum, entity.datum) &&
                Objects.equals(this.cashRegisterId, entity.cashRegisterId) &&
                Objects.equals(this.documentNo, entity.documentNo) &&
                Objects.equals(this.databaseName, entity.databaseName) &&
                Objects.equals(this.positionNo, entity.positionNo) &&
                Objects.equals(this.serverName, entity.serverName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datum, cashRegisterId, documentNo, databaseName, positionNo, serverName);
    }
}
