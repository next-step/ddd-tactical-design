package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import kitchenpos.common.vo.Price;

import java.util.Objects;

@Entity
public class Product {

    @EmbeddedId
    private ProductId id;

    @Embedded
    private ProductName name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(ProductId id, ProductName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void changePrice(Price newPrice) {
        this.price = newPrice;
    }

    public void changePrice(long newPrice) {
        this.price = new Price(newPrice);
    }

    public ProductId getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
