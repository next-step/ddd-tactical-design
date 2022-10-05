package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.global.vo.DisplayedName;
import kitchenpos.global.vo.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

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

    public Menu(DisplayedName name, Price price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        validatePrice(price.getValue(), menuProducts.getMenuProductsTotalPrice());

        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void changePrice(Price price) {
        validatePrice(price.getValue(), menuProducts.getMenuProductsTotalPrice());
        this.price = price;
    }

    public void changeMenuProductPrice(UUID productId, Price price) {
        menuProducts.changePrice(productId, price);
        if (priceCompare(this.price.getValue(), menuProducts.getMenuProductsTotalPrice())) {
            hide();
        }
    }

    public void show() {
        validatePrice(price.getValue(), menuProducts.getMenuProductsTotalPrice());

        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    private void validatePrice(BigDecimal price, BigDecimal target) {
        if (price.compareTo(target) > 0) {
            throw new IllegalArgumentException("메뉴 가격은 메뉴속 상품의 총합보다 크면 안됩니다.");
        }
    }

    private boolean priceCompare(BigDecimal price, BigDecimal target) {
        return price.compareTo(target) > 0;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
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
}
