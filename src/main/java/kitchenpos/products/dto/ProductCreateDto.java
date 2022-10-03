package kitchenpos.products.dto;

import java.math.BigDecimal;
import kitchenpos.products.domain.Product;
import kitchenpos.products.infra.PurgomalumClient;

public class ProductCreateDto {

    private String name;
    private BigDecimal price;

    ProductCreateDto(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toProduct(final PurgomalumClient purgomalumClient) {
        return new Product(name, price, purgomalumClient);
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
