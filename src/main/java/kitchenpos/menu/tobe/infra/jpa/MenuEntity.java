package kitchenpos.menu.tobe.infra.jpa;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu")
@Entity
public class MenuEntity {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    public UUID id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "menu_group_id", columnDefinition = "binary(16)", nullable = false)
    public UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    public boolean displayed;

    @Column(name = "price", nullable = false)
    public BigDecimal price;

    public MenuEntity() {
    }
}
