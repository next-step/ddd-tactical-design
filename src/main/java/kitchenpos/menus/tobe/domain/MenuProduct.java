package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import kitchenpos.common.model.Price;

public class MenuProduct {
    //private Long seq;
    //private Long menuId;
    private Long productId;
    private long quantity;
    private Price productPrice;

    public MenuProduct(Long productId, long quantity, Price productPrice) {
       this.productId = productId;
       this.quantity = quantity;
       this.productPrice = productPrice;
    }

    public MenuProduct(Long productId, long quantity, BigDecimal productPrice) {
        this(productId, quantity, Price.of(productPrice));
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    Price priceSum() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
