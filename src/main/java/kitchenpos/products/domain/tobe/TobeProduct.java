package kitchenpos.products.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.products.domain.PurgomalumClient;

@Table(name = "product")
@Entity
public class TobeProduct {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    public TobeProduct() {
    }

    public TobeProduct(String name, PurgomalumClient purgomalumClient, BigDecimal price) {
        this(UUID.randomUUID(), new Name(name, purgomalumClient), new Price(price));
    }

    public TobeProduct(UUID id, Name name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changePrice(BigDecimal bigDecimal) {
        this.price = new Price(bigDecimal);
    }

    public boolean isSamePrice(Price price) {
        return this.price.equals(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TobeProduct product = (TobeProduct) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
