package kitchenpos.products.ui.dto;

import java.math.BigDecimal;
import kitchenpos.products.domain.ProfanityValidator;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductName;
import kitchenpos.products.domain.tobe.ProductPrice;

public class ProductCreateRequest {

    private final ProductName name;

    private final ProductPrice price;

    public ProductCreateRequest(String name, BigDecimal price) {
        this(new ProductName(name), new ProductPrice(price));
    }

    public ProductCreateRequest(ProductName name, ProductPrice price) {
        this.name = name;
        this.price = price;
    }

    public void validateName(ProfanityValidator profanityValidator) {
        profanityValidator.validate(name.getName());
    }

    public Product to() {
        return new Product(name, price);
    }
}
