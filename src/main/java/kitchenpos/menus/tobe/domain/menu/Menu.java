package kitchenpos.menus.tobe.domain.menu;


import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.products.tobe.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    private final MenuName menuName;
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;
    private final MenuProducts menuProducts;
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

    public Menu(MenuName menuName, Price price, MenuProducts menuProducts, MenuGroup menuGroup) {
        this.menuName = menuName;
        this.menuGroup = menuGroup;
        this.price = price;
        this.menuProducts = menuProducts;
        validate(price, menuProducts, menuGroup);
    }

    private void validate(Price price, MenuProducts menuProducts, MenuGroup menuGroup) {
        validateMenuProductsSize(menuProducts);
        validatePrice(price, menuProducts);
        validateMenuGroup(menuGroup);
    }

    private void validateMenuGroup(MenuGroup menuGroup) {
        if (menuGroup == null) {
            throw new IllegalArgumentException("메뉴는 특정 메뉴 그룹에 속해야 한다.");
        }
    }

    private void validatePrice(Price price, MenuProducts menuProducts) {
        if (isMenuPriceOverMenuProductsSum(price, menuProducts)) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작을 수 없습니다.");
        }
        if (price.price().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴의 가격은 0원 이상이어야 한다.");
        }
    }

    private boolean isMenuPriceOverMenuProductsSum(Price price, MenuProducts menuProducts) {
        return price.price().compareTo(menuProducts.sum()) > 0;
    }

    private static void validateMenuProductsSize(MenuProducts menuProducts) {
        if (menuProducts.size() < 1) {
            throw new IllegalArgumentException("메뉴 상품을 등록해주세요.");
        }
    }

    public void hide() {
        this.displayed = false;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void changePrice(Price price) {
        validatePrice(price, this.menuProducts);
        this.price = price;
    }

    public Price price() {
        return this.price;
    }

    public void display() {
        if (!isMenuPriceOverMenuProductsSum(this.price, this.menuProducts)) {
            this.displayed = true;
        }
    }

    public UUID getId() {
        return this.id;
    }
}
