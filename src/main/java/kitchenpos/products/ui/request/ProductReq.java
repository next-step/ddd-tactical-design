package kitchenpos.products.ui.request;

import kitchenpos.products.common.NamePolicy;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductReq {

    private BigDecimal price;

    private String name;

    public ProductReq(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toEntity(NamePolicy namePolicy) {
        return Product.builder()
                .id(UUID.randomUUID())
                .name(name, namePolicy)
                .price(price)
                .build();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}
