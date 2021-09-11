package kitchenpos.menus.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.tobe.domain.Price;

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
    // TODO 존재하는 product id에 대한 validation 처리

    protected MenuProduct() {
    }

    public MenuProduct(final UUID productId, final Quantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // TODO 해당 product의 가격을 어떻게 처리할지 결정
    public Price getMenuProductPrice() {
        // 해당 productId의 가격 get
        // quantity와 곱해서 리턴
        return new Price(0L);
    }

}
