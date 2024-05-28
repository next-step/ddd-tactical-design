package kitchenpos.domain.menu.tobe.domain;

import jakarta.persistence.*;

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
    private MenuName name;

    @Embedded
    private MenuPrice price;

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
        MenuPrice newMenuPrice = new MenuPrice(price);
        MenuProducts newMenuProducts = new MenuProducts(menuProducts);
        validateOverMenuPrice(newMenuPrice, newMenuProducts);

        this.id = id;
        this.name = new MenuName(name);
        this.price = newMenuPrice;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = newMenuProducts;
    }

    public void changePrice(BigDecimal price) {
        // validate price and menuProducts price sum
        MenuPrice changeMenuPrice = new MenuPrice(price);
        validateOverMenuPrice(changeMenuPrice, menuProducts);
        this.price = changeMenuPrice;
    }

    private void validateOverMenuPrice(MenuPrice menuPrice, MenuProducts menuProducts) {
        BigDecimal price = menuPrice.getPrice();
        if (price.compareTo(menuProducts.getTotalPrice()) > 0) {
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

    public boolean displayed() {
        return displayed;
    }

    public String name() {
        return name.getName();
    }

    public BigDecimal price() {
        return price.getPrice();
    }
}
