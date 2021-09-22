package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;
import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.model.ProductPrice;

public class ProductRequestDto {

    private String name;
    private BigDecimal price;
    private final Profanities profanities;

    protected ProductRequestDto(final Profanities profanities) {
        this.profanities = profanities;
    }

    public ProductRequestDto(final String name, final BigDecimal price, final Profanities profanities) {
        this.name = name;
        this.price = price;
        this.profanities = profanities;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product toEntity() {
        return new Product(new DisplayedName(this.name, profanities), new ProductPrice(price));
    }

}
