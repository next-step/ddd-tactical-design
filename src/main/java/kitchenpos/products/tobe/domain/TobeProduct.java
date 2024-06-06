package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.shared.domain.Profanities;

import java.util.UUID;

@Table(name = "tobe_product")
@Entity
public class TobeProduct {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

    private TobeProduct() {
    }

    public TobeProduct(UUID id, String name, int price, Profanities profanities) {
        this.id = id;
        this.name = DisplayedName.of(name, profanities);
        this.price = Price.of(price);
    }

    public void changePrice(int price) {
        this.price = Price.of(price);
    }

    public String getName() {
        return name.getName();
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                '}';
    }
}
