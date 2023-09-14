package kitchenpos.product.tobe.domain;

import kitchenpos.profanity.domain.PurgomalumChecker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "product")
@Entity
public class NewProduct {
    @Column(columnDefinition = "binary(16)")
    @Id
    private UUID id;

    private Name name;

    private Price price;

    protected NewProduct() {
    }

    public NewProduct(final UUID id, final Name name, final Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static NewProduct createWithoutProfanity(final Name name, final Price price, final PurgomalumChecker purgomalumChecker) {
        if (purgomalumChecker.containsProfanity(name.getValue())) {
            throw new IllegalNewProductNameException();
        }

        return new NewProduct(UUID.randomUUID(), name, price);
    }

    public void changePrice(final Price price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
