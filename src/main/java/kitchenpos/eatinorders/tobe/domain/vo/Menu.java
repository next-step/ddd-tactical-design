package kitchenpos.eatinorders.tobe.domain.vo;

import static java.util.Objects.isNull;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.ValueObject;

@Embeddable
public class Menu implements ValueObject {

    @Column(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
    private UUID id;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "menu_name", nullable = false)
    )
    private Name name;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "menu_price", nullable = false)
    )
    private Price price;

    protected Menu() {
    }

    public Menu(UUID id, Name name, Price price) {
        validate(id);
        this.id = id;
        this.name = name;
        this.price = price;
    }

    private void validate(UUID id) {
        if (isNull(id)) {
            throw new IllegalArgumentException();
        }
    }

    public UUID getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
