package kitchenpos.menus.tobe.domain.menu.vo;

import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;

import java.math.BigDecimal;

public class MenuProductVO {
    private PositiveNumber productId;
    private PositiveNumber quantity;
    private Price price;

    public MenuProductVO(MenuProductVO menuProductVO){
        this(menuProductVO.productId.valueOf(), menuProductVO.quantity.valueOf());
    }

    public MenuProductVO(Long productId, Long quantity) {
        this(productId, quantity, null);
    }

    public MenuProductVO(Long productId, Long quantity, BigDecimal price){
        this.productId = new PositiveNumber(productId);
        this.quantity = new PositiveNumber(quantity);
        this.price = new Price(price);
    }

    public BigDecimal getPrice (){
        return price.valueOf();
    }

    public Long getProductId (){
        return productId.valueOf();
    }

    public Price acount (){
        return price.multiply(quantity.valueOf());
    }

    public Long getQuantity() {
        return quantity.valueOf();
    }
}
