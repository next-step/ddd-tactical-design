package kitchenpos.menu.tobe.infra.jpa;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu_product")
@Entity
public class MenuProductEntity {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Long seq;

    @Column(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
    public UUID menuId;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    public UUID productId;

    @Column(name = "price", nullable = false)
    public BigDecimal price;

    @Column(name = "quantity", nullable = false)
    public Long quantity;

    public MenuProductEntity() {
    }
}
