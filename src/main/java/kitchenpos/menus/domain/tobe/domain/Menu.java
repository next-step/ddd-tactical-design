package kitchenpos.menus.domain.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    private static final String MENU_PRICE_MUST_BE_LESS_THAN_OR_EQUAL = "메뉴의 가격은 메뉴 상품 목록 금액의 합보다 적거나 같아야 합니다.";

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(String name, BigDecimal price, String menuGroupName, List<MenuProduct> menuProducts) {
        this.id = UUID.randomUUID();
        this.name = new DisplayedName(name);
        this.price = new MenuPrice(price);
        this.menuGroup = new MenuGroup(menuGroupName);
        this.displayed = true;
        this.menuProducts = new MenuProducts(menuProducts);
        validate();
    }

    private void validate() {
        if (isInvalidPrice()) {
            throw new IllegalArgumentException(MENU_PRICE_MUST_BE_LESS_THAN_OR_EQUAL);
        }
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    public MenuPrice getPrice() {
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

    public void changePrice(BigDecimal price) {
        this.price = new MenuPrice(price);
        hideWhenInvalid();
    }

    private void hideWhenInvalid() {
        if (isInvalidPrice()) {
            hide();
        }
    }

    private boolean isInvalidPrice() {
        return this.price.isInvalid(menuProducts.getTotalAmount());
    }

    public void hide() {
        this.displayed = false;
    }

    public void display() {
        validate();
        this.displayed = true;
    }
}
