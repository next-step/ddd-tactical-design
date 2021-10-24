package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu")
@Entity(name = "orderMenu")
public class Menu {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal menuPrice;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    protected Menu() {
    }

    public Menu(UUID id, String name, BigDecimal menuPrice, boolean displayed) {
        this.id = id;
        this.name = name;
        this.menuPrice = menuPrice;
        this.displayed = displayed;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMenuPrice() {
        return menuPrice;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
