package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    @ManyToOne(optional = false)
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
    }

    public Menu(Name name, Price price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this(null, name, price, menuGroup, displayed, menuProducts);
    }

    public Menu(UUID id, Name name, Price price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.validate(price, menuProducts);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static Menu from(UUID id, String name, MenuNamePolicy menuNamePolicy, BigDecimal price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        return new Menu(
                id,
                Name.from(name, menuNamePolicy),
                Price.from(price),
                menuGroup,
                displayed,
                menuProducts
        );
    }

    private void validate(Price price, MenuProducts menuProducts) {
        if (price.isBiggerThan(menuProducts.totalAmount())) {
            throw new IllegalArgumentException();
        }
    }

    public void changeMenuProductPrice(UUID productId, BigDecimal price) {
        this.menuProducts.changeMenuProductsPrice(productId, price);
        if (this.isPriceBiggerThanMenuProductsTotalAmount()) {
            this.displayed = false;
        }
    }

    private boolean isPriceBiggerThanMenuProductsTotalAmount() {
        Price totalAmount = this.menuProducts.totalAmount();
        return this.price.isBiggerThan(totalAmount);
    }

    public void changePrice(BigDecimal price) {
        Price menuPrice = Price.from(price);
        if (menuPrice.isBiggerThan(this.menuProducts.totalAmount())) {
            throw new IllegalArgumentException();
        }
        this.price = menuPrice;
    }

    public void display() {
        if (price.isBiggerThan(this.menuProducts.totalAmount())) {
            throw new IllegalStateException();
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public BigDecimal priceMultiplyByQuantity(long quantity) {
        return this.price.multiplyByQuantity(quantity).getValue();
    }

    public boolean isHide() {
        return !this.displayed;
    }

    public BigDecimal getPriceValue() {
        return this.price.getValue();
    }

    public String getNameValue() {
        return this.name.getValue();
    }

    public UUID getId() {
        return id;
    }


    public Name getName() {
        return name;
    }


    public Price getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }


    public boolean isDisplayed() {
        return displayed;
    }


    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
