package kitchenpos.products.tobe.domain.entity;

import kitchenpos.products.tobe.domain.ProductRepositoryImpl;
import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;

import java.util.UUID;


public class Product {
    private final UUID id;
    private final DisplayedName displayedName;
    private final Price price;

    public Product(UUID id, DisplayedName displayedName, Price price) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.displayedName = displayedName;
        this.price = price;
    }

    public Product(DisplayedName displayedName, Price price) {
        this(UUID.randomUUID(), displayedName, price);
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


    public void register(ProductRepositoryImpl repo) {
        Product product = new Product(this.getId(), this.displayedName, this.price);
        repo.save(product);
    }
}
