package kitchenpos.products.ui.request;


import kitchenpos.products.infra.ProductProfanity;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;

public class ProductCreateRequest {

    private final ProductName name;

    private final ProductPrice price;

    public ProductCreateRequest(final String name, final BigDecimal price) {
        this(new ProductName(name), new ProductPrice(price));
    }

    public ProductCreateRequest(final ProductName name, final ProductPrice price) {
        this.name = name;
        this.price = price;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void checkProfanityName(final ProductProfanity profanityChecker) {
        profanityChecker.profanityCheck(name.getName());
    }

    public Product toEntity() {
        return new Product(name, price);
    }
}
