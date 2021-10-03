package kitchenpos.menus.tobe.menu.ui.dto;

import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.ProductId;

import java.util.Objects;
import java.util.UUID;

public class ProductResponse {
    private ProductId productId;
    private Price price;

    protected ProductResponse() {
    }

    public ProductResponse(final UUID productId, final Price price) {
        this(new ProductId(productId), price);
    }

    public ProductResponse(final ProductId productId, final Price price) {
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
        final ProductResponse productVO = (ProductResponse) o;
        return Objects.equals(productId, productVO.productId) && Objects.equals(price, productVO.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price);
    }
}
