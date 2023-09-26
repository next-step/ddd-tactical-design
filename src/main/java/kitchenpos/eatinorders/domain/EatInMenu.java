package kitchenpos.eatinorders.domain;

import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "eat_in_menu")
@Entity
public class EatInMenu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Price price;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    public EatInMenu() {
    }

    public EatInMenu(UUID id, Price price, boolean displayed) {
        this.id = id;
        this.price = price;
        this.displayed = displayed;
    }

    public static EatInMenu create(UUID id, Price price, boolean displayed) {
        return new EatInMenu(id, price, displayed);
    }

    public UUID getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInMenu newMenu = (EatInMenu) o;
        return Objects.equals(id, newMenu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isNotEqualTo(Price orderLineItemPrice) {
        return price.isNotEqualTo(orderLineItemPrice);
    }
}
