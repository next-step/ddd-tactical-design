package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "menu_id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private MenuName name;

    @Column(name = "price", nullable = false)
    private MenuPrice price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
        if (isPriceLessThanTotal()) {
            throw new IllegalArgumentException();
        }
    }

    public void hideIfMenuPriceTooHigher() {
        if (isPriceLessThanTotal()) {
            this.displayed = false;
        }
    }

    public UUID getMenuGroupId() {
        return menuGroup.getId();
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public String getName() {
        return name.value();
    }

    public void changePrice(BigDecimal ten) {
        if (isPriceLessThanTotal()) {
            throw new IllegalArgumentException();
        }
        this.price = new MenuPrice(ten);
    }

    private boolean isPriceLessThanTotal() {
        return price.value().compareTo(menuProducts.getTotalPrice()) < 0;
    }

    public void setDisplayable() {
        if (isPriceLessThanTotal()) {
            throw new IllegalArgumentException();
        }
        this.displayed = true;
    }

    public void setHide() {
        this.displayed = false;
    }
}
