package kitchenpos.menus.tobe.domain.entity;

import kitchenpos.common.domain.vo.Name;
import kitchenpos.common.domain.vo.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id")
    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "price", nullable = false))
    private Price price;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "productName", nullable = false))
    private Name name;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private final List<MenuProduct> menuProducts = new ArrayList<>();

    public Menu(Price price, Name name, boolean displayed, MenuGroup menuGroup) {
        this.id = UUID.randomUUID();
        this.price = price;
        this.name = name;
        this.displayed = displayed;
        this.menuGroup = menuGroup;
    }

    public void registerMenuProduct(MenuProduct menuProduct) {
        this.menuProducts.add(menuProduct);

        if (! isReasonablePrice()) {
            throw new IllegalArgumentException();
        }
    }

    public void changePrice(Price price) {
        this.price = price;

        if (! isReasonablePrice()) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isReasonablePrice() {
        return price.getPrice().compareTo(originalPrice().getPrice()) <= 0;
    }

    private Price originalPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : this.menuProducts) {
            sum = sum.add(menuProduct.totalPrice().getPrice());
        }
        return new Price(sum);
    }

    public void display() {
        if (! isReasonablePrice()) {
            throw new IllegalStateException();
        }

        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public UUID getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public Name getName() {
        return name;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    protected Menu() {}
}
