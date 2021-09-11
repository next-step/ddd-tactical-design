package kitchenpos.menus.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    @Column(name = "quantity", nullable = false)
    private Quantity quantity;

    @Column(
        name = "product_id",
        nullable = false,
        columnDefinition = "varbinary(16)"
    )
    private UUID productId;

    protected MenuProduct() {
    }

    public MenuProduct(final UUID productId, final Quantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
