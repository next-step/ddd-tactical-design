package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Product {
    private static final String INVALID_NAME_MESSAGE = "상품의 이름으로 사용할 수 없습니다.";

    private final UUID id;
    private final String name;
    private ProductPrice price;

    public Product(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        validateName(name, purgomalumClient);

        this.id = UUID.randomUUID();
        this.name = name;
        this.price = new ProductPrice(price);
    }

    private void validateName(String name, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(INVALID_NAME_MESSAGE);
        }
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }

    public ProductPrice getPrice() {
        return price;
    }

    public BigDecimal multiplyPrice(BigDecimal quantity) {
        return price.multiplyPrice(quantity);
    }
}
