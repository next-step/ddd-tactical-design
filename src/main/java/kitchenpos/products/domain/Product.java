package kitchenpos.products.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {
    private static final String INVALID_NAME_MESSAGE = "상품의 이름으로 사용할 수 없습니다.";

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private ProductPrice price;

    protected Product() {

    }

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
        return this.price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
