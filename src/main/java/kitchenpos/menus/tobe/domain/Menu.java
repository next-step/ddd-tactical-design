package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.domain.MenuGroup;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    private static final String CAN_NOT_DISPLAY_MESSAGE = "메뉴의 가격은 상품 가격의 합보다 작다면 전시할 수 없습니다.";

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Price price;

    @Embedded
    private DisplayedName name;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    @Transient
    private UUID menuGroupId;

    public Menu(final Price price, final DisplayedName name, final MenuProduct menuProduct, final MenuGroup menuGroup) {
        decideDisplay(menuProduct, price);
        this.price = price;
        this.name = name;
        this.menuProduct = menuProduct;
        this.menuGroup = menuGroup;
    }

    public Menu() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(final Price price) {
        this.price = price;
    }

    public DisplayedName getName() {
        return name;
    }

    public void setName(final DisplayedName name) {
        this.name = name;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(final MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public MenuProduct getMenuProduct() {
        return menuProduct;
    }

    public void setMenuProduct(final MenuProduct menuProduct) {
        this.menuProduct = menuProduct;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public void changePrice(final Price price) {
        decideDisplay(this.menuProduct, price);
        this.price = price;
    }

    public boolean isDisplayed() {
        return this.displayed;
    }

    public void displayOff() {
        this.displayed = false;
    }

    public void displayOn() {
        validateDisplay();
        this.displayed = true;
    }

    private void decideDisplay(final MenuProduct menuProduct, final Price price) {
        if (menuProduct.lessThan(price)) {
            this.displayed = false;
            return;
        }
        this.displayed = true;
    }

    private void validateDisplay() {
        if (menuProduct.lessThan(price)) {
            throw new IllegalArgumentException(CAN_NOT_DISPLAY_MESSAGE);
        }
    }
}
