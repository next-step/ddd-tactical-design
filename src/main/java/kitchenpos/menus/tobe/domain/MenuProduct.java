package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

public class MenuProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq")
    private Long seq;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    private MenuProductPrice menuProductPrice;

    public MenuProduct(Long menuId, Long productId, long quantity, MenuProductPrice menuProductPrice) {
        this.menuId = menuId;
        this.productId = productId;
        this.quantity = quantity;
        this.menuProductPrice = menuProductPrice;
    }

    public BigDecimal getMenuProductPrice(){
        return menuProductPrice.getValue();
    }

    public Long getMenuId() {
        return menuId;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
