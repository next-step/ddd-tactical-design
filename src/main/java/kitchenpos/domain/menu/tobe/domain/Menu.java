package kitchenpos.domain.menu.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.domain.support.Name;
import kitchenpos.domain.support.Price;

import java.math.BigDecimal;
import java.util.List;
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

    public Menu(String name, BigDecimal price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        this(UUID.randomUUID(), name, price, menuGroup, displayed, menuProducts);
    }

    public Menu(UUID id, String name, BigDecimal price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        Price newMenuPrice = new Price(price);
        MenuProducts newMenuProducts = new MenuProducts(menuProducts);
        validateOverMenuPrice(newMenuPrice, newMenuProducts);

        this.id = id;
        this.name = new Name(name);
        this.price = newMenuPrice;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = newMenuProducts;
    }

    public void changePrice(BigDecimal price) {
        // validate price and menuProducts price sum
        Price changeMenuPrice = new Price(price);
        validateOverMenuPrice(changeMenuPrice, menuProducts);
        this.price = changeMenuPrice;
    }

    private void validateOverMenuPrice(Price menuPrice, MenuProducts menuProducts) {
        if (menuPrice.isOver(menuProducts.getTotalPrice())) {
            throw new IllegalStateException("메뉴 가격은 메뉴 상품 가격의 합보다 초과할 수 없습니다.");
        }
    }

    public void hide() {
        this.displayed = false;
    }

    public void display() {
        validateOverMenuPrice(price, menuProducts);
        this.displayed = true;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public String name() {
        return name.getName();
    }

    public BigDecimal price() {
        return price.getPrice();
    }
}
