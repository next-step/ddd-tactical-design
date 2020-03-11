package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_product")
public class MenuProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Long quantity;

    public MenuProduct() {

    }

    public MenuProduct(Long menuId, Long productId, Long quantity) {
        this.menuId = menuId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal applyQuantity(BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
