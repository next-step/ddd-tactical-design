package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayedName name;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;


}
