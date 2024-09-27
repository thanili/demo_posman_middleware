package org.example.source_api.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
@Embeddable
public class ApiUserId {
    private static final long serialVersionUID = 8066770631547262990L;

    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "company", nullable = false)
    private String company;
    @Column(name = "username", nullable = false)
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiUserId apiUserId = (ApiUserId) o;
        return Objects.equals(id, apiUserId.id) && Objects.equals(company, apiUserId.company) && Objects.equals(username, apiUserId.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company, username);
    }
}
