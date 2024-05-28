package kitchenpos.products.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.products.infra.PurgomalumClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Product는 식별자와 DisplayedName, 가격을 가진다.
 * DisplayedName에는 Profanity가 포함될 수 없다.
 */

@Table(name = "product")
@Entity
@Getter
public class Product {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private DispayedName dispayedName;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    @Autowired
    private static PurgomalumClient defaultPurgomalumClient;

    protected Product() {
    }

    private Product(final DispayedName name, final Price price) {
        this.id = UUID.randomUUID();
        this.dispayedName = name;
        this.price = price;
    }

    public static final Product createProduct(final String name, final BigDecimal price, final Profanities profanities){
        DispayedName displayedName = new DispayedName(name, profanities);
        Price displayedPrice = Price.createPrice(price);

        return new Product(displayedName, displayedPrice);
    }

    public Product changePrice(final Price price){
        this.price = this.price.changePrice(price);

        return this;
    }

}
