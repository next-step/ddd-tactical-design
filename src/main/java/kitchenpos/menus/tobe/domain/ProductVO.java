package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.ProductId;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

public class ProductVO {
    private ProductId productId;
    private Price price;

    protected ProductVO() {
    }

    public ProductVO(final UUID productId, final Price price) {
        this(new ProductId(productId), price);
    }

    public ProductVO(final ProductId productId, final Price price) {
        this.productId = productId;
        this.price = price;
    }

    public UUID getId() {
        return productId.getId();
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductVO productVO = (ProductVO) o;
        return Objects.equals(productId, productVO.productId) && Objects.equals(price, productVO.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price);
    }
}
