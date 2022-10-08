package kitchenpos.menus.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import kitchenpos.menus.exception.InvalidMenuPriceException;

@Table(name = "menu")
@Entity
public class Menu {

    @Id
    @Column(
        name = "id",
        length = 16,
        unique = true,
        nullable = false
    )
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @ManyToOne
    @JoinColumn(name = "menu_group_id", nullable = false, columnDefinition = "binary(16)")
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(
        MenuName name,
        MenuPrice price,
        MenuGroup menuGroup,
        MenuProducts menuProducts
    ) {
        this(UUID.randomUUID(), name, price, menuGroup, menuProducts);
    }

    public Menu(
        UUID id,
        MenuName name,
        MenuPrice price,
        MenuGroup menuGroup,
        MenuProducts menuProducts
    ) {
        this.id = id;
        this.name = name;
        this.menuGroup = menuGroup;
        this.displayed = true;
        this.price = price;
        this.menuProducts = menuProducts;
        this.menuProducts.enrollMenu(this);
        validateMenuPrice(this.price, this.menuProducts);
    }

    private void validateMenuPrice(MenuPrice price, MenuProducts menuProducts) {
        if (price.isBiggerThan(menuProducts.getSumOfPrice())) {
            throw new InvalidMenuPriceException("메뉴의 가격은 상품 가격들의 합보다 클 수 없습니다.");
        }
    }

    public void hide() {
        displayed = false;
    }

    public void display() {
        if (price.isBiggerThan(menuProducts.getSumOfPrice())) {
            throw new InvalidMenuPriceException("메뉴의 가격이 상품보다 높아 전시상태를 변경할 수 없습니다.");
        }

        displayed = true;
    }

    public void changePrice(MenuPrice newPrice) {
        this.price = newPrice;
        if (this.price.isBiggerThan(menuProducts.getSumOfPrice())) {
            displayed = false;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public UUID getMenuGroupId() {
        return menuGroup.getId();
    }

    public List<MenuProduct> getMenuProductValues() {
        return menuProducts.getValues();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
