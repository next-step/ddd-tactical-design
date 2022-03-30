package kitchenpos.products.tobe.domain.model;

import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import kitchenpos.global.domain.vo.Price;
import kitchenpos.products.tobe.domain.model.vo.ProductName;

import java.math.BigDecimal;
import java.util.UUID;


public final class Product {

    private final UUID id;
    private final ProductName name;
    private Price price;

    public Product(String name, BigDecimal price, BannedWordCheckClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.name = new ProductName(name, purgomalumClient);
        this.price = new Price(price);
    }

    public void changePrice(BigDecimal price) {
        if(this.price.isNotSame(price)) {
            this.price = new Price(price);
        }
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
