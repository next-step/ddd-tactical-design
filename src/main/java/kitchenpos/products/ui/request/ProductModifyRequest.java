package kitchenpos.products.ui.request;

import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;

public class ProductModifyRequest {

    private final ProductPrice price;

    public ProductModifyRequest(long price) {
        this(new ProductPrice(BigDecimal.valueOf(price)));
    }

    public ProductModifyRequest(ProductPrice price) {
        this.price = price;
    }

    public ProductPrice getPrice() {
        return price;
    }
}
