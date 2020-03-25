package kitchenpos.menus.tobe.domain.menu.vo;

import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;

import java.math.BigDecimal;

public class NewMenuProduct {
    private PositiveNumber productId;
    private PositiveNumber quantity;
    private Price price;

    public NewMenuProduct (NewMenuProduct newMenuProduct){
        this(newMenuProduct.productId.valueOf(), newMenuProduct.quantity.valueOf());
    }

    public NewMenuProduct(Long productId, Long quantity) {
        this(productId, quantity, null);
    }

    public NewMenuProduct(Long productId, Long quantity, BigDecimal price){
        this.productId = new PositiveNumber(productId);
        this.quantity = new PositiveNumber(quantity);
        this.price = new Price(price);
    }

    public Long getProductId (){
        return productId.valueOf();
    }

    public Long getQuantity() {
        return quantity.valueOf();
    }
}
