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
public class DocumentId implements Serializable {
    private static final long serialVersionUID = -2886128774200694197L;

    @Column(name = "ServerName", nullable = false, length = 128)
    private String serverName;

    @Column(name = "DatabaseName", nullable = false, length = 128)
    private String databaseName;

    @Column(name = "CashRegisterId", nullable = false)
    private Integer cashRegisterId;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "DocumentNo", nullable = false)
    private Integer documentNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DocumentId entity = (DocumentId) o;
        return Objects.equals(this.date, entity.date) &&
                Objects.equals(this.cashRegisterId, entity.cashRegisterId) &&
                Objects.equals(this.documentNo, entity.documentNo) &&
                Objects.equals(this.databaseName, entity.databaseName) &&
                Objects.equals(this.serverName, entity.serverName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, cashRegisterId, documentNo, databaseName, serverName);
    }
}
