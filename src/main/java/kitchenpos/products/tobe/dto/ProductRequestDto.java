package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.model.DisplayedName;
import kitchenpos.products.tobe.domain.model.Price;
import kitchenpos.products.tobe.domain.model.Product;

public class ProductRequestDto {

    private String name;
    private BigDecimal price;

    private final PurgomalumClient purgomalumClient;

    protected ProductRequestDto(final PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public ProductRequestDto(final String name, final BigDecimal price, final PurgomalumClient purgomalumClient) {
        this.name = name;
        this.price = price;
        this.purgomalumClient = purgomalumClient;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product toEntity() {
        return new Product(new DisplayedName(this.name, purgomalumClient), new Price(price));
    }

}
