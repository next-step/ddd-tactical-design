package kitchenpos.products.tobe.domain.model.entity;

import java.util.UUID;
import kitchenpos.global.util.Assert;
import kitchenpos.products.tobe.domain.model.vo.ProductName;
import kitchenpos.products.tobe.domain.model.vo.ProductPrice;

public class Product {

    private final UUID id;

    private final ProductName name;

    private ProductPrice price;

    public Product(ProductName name, ProductPrice price) {
        Assert.notNull(name, "상품 이름은 필수값입니다.");
        Assert.notNull(price, "상품 가격은 필수값입니다.");
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    public void changePrice(ProductPrice price) {
        Assert.notNull(price, "상품 가격은 필수값입니다.");
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }
}
