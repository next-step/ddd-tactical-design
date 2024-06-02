package kitchenpos.products.tobe.domain.entity;

import kitchenpos.products.tobe.domain.strategy.Profanity;
import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;

import java.util.UUID;


public class Product {
    private final UUID id;
    private final DisplayedName displayedName;
    private final Price price;
    private final Profanity profanity;

    public Product(UUID id, DisplayedName displayedName, Price price, Profanity profanity) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.displayedName = displayedName;
        this.price = price;
        this.profanity = profanity;
    }

    // Constructor that generates a random UUID
    public Product(DisplayedName displayedName, Price price) {
        this(UUID.randomUUID(), displayedName, price, new Profanity());
    }

    public UUID getId() {
        return this.id;
    }

    public Price getPrice() {
        return this.price;
    }

    public DisplayedName getName() {
        return this.displayedName;
    }

    public void validateProperty() {
        this.checkValidPrice();
        this.checkValidName();
    }

    public void checkValidName() {
        this.profanity.validate(this.displayedName.value());
    }

    private void checkValidPrice() {
        if (this.price.getValue() <= 0) {
            throw new IllegalArgumentException("Price should be over zero, not negative");
        }
    }
}
