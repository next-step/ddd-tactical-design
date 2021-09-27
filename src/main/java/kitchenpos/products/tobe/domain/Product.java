package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private UUID id;
    @Embedded
    private DisplayedName displayedName;
    @Embedded
    private Price price;

    public Product() {
    }

    public Product(DisplayedName displayedName, Price price) {
        this.displayedName = displayedName;
        this.price = price;
    }
}
