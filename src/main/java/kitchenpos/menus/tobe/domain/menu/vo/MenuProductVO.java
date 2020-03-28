package kitchenpos.menus.tobe.domain.menu.vo;

import kitchenpos.common.Index;
import kitchenpos.common.PositiveNumber;
import kitchenpos.common.Price;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuProductVO {
    private Index productId;
    private PositiveNumber quantity;
    private Price price;

    public MenuProductVO(MenuProductVO menuProductVO){
        this(menuProductVO.productId.valueOf(), menuProductVO.quantity.valueOf());
    }

    public MenuProductVO(Long productId, Long quantity) {
        this(productId, quantity, null);
    }

    public MenuProductVO(Long productId, Long quantity, BigDecimal price){
        this.productId = new Index(productId);
        this.quantity = new PositiveNumber(quantity);
        this.price = new Price(price);
    }

    public BigDecimal getPrice (){
        return price.valueOf();
    }

    public Long getProductId (){
        return productId.valueOf();
    }

    public Long getQuantity() {
        return quantity.valueOf();
    }

    public BigDecimal getAccount (){
        return this.getPrice()
            .multiply(
            new BigDecimal(this.getQuantity())
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductVO that = (MenuProductVO) o;
        return Objects.equals(productId, that.productId) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity, price);
    }
}
