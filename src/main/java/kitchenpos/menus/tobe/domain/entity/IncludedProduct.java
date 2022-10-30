package kitchenpos.menus.tobe.domain.entity;

import kitchenpos.common.domain.vo.Price;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "product")
@Entity
public class IncludedProduct {
    @Column(name = "id")
    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "price", nullable = false))
    private Price price;

    public IncludedProduct(Price price) {
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public UUID getId() {
        return id;
    }

    protected IncludedProduct() {}
}
