package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    protected Menu() {
    }

    public Menu(UUID id, BigDecimal price, boolean displayed) {
        this.id = id;
        this.price = price;
        this.displayed = displayed;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
