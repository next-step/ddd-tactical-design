package kitchenpos.menu.tobe.infra.jpa;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MenuGroupEntity {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    public UUID id;

    @Column(name = "name", nullable = false)
    public String name;

    public MenuGroupEntity() {
    }
}
