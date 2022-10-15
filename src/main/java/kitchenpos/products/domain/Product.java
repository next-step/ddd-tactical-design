package kitchenpos.products.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.util.ObjectUtils;


@Entity
@Table(name = "product")
public class Product {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    protected Product() {

    }

    public Product(UUID id, DisplayedName name, Price price) {
        this(name, price);
        this.id = id;
    }

    public Product(DisplayedName displayedName, Price price) {
        validate(displayedName, price);
        this.displayedName = displayedName;
        this.price = price;
    }

    private void validate(DisplayedName displayedName, Price price) {
        if (ObjectUtils.isEmpty(displayedName)) {
            throw new IllegalArgumentException("이름은 필수 입니다.");
        }
        if (ObjectUtils.isEmpty(price)) {
            throw new IllegalArgumentException("가격은 필수 입니다.");
        }
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getDisplayedName() {
        return displayedName;
    }

    public Price getPrice() {
        return price;
    }

    public void changePrice(Price price) {
        this.price = price;
    }
}

