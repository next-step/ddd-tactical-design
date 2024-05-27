package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.Name;
import kitchenpos.products.tobe.domain.vo.Price;

import java.util.UUID;


public class Product {
    private final UUID id;
    private final Name name;
    private final Price price;

    public Product(UUID id, Name name, Price price) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.name = name;
        this.price = price;
    }

    // Constructor that generates a random UUID
    public Product(Name name, Price price) {
        this(UUID.randomUUID(), name, price);
    }

    public UUID getId() {
        return this.id;
    }

    public Price getPrice(){
        return this.price;
    }

    public Name getName(){
        return this.name;
    }

    public void validateProperty() {
        this.checkValidPrice(this.price);
        this.checkValidName(this.name);
    }

    private void checkValidName(Name name) {
        // add third-party service to check name
    }

    private void checkValidPrice(Price price) {
        if (price.getValue() <= 0) {
            throw new IllegalArgumentException("Price should be over zero, not negative");
        }
    }
}
