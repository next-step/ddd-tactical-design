package kitchenpos.products.tobe.domain;

import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isBlank(name) || profanities.contains(name)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.name = DisplayedName.of(name);
        this.price = Price.of(price);
    }

    public void changePrice(int price) {
        this.price = Price.of(price);
    }

    public String getName() {
        return name.getName();
    }

    public int getPrice() {
        return price.getPrice();
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
