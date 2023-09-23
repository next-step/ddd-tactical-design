package kitchenpos.menus.domain.menu;

import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.domain.menugroup.MenuGroup;

import javax.persistence.*;
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

    protected Menu() {
    }

    public Menu(final MenuGroup menuGroup, final Name name, final Price price, final boolean displayed, final MenuProducts menuProducts) {
        if (isMenuPriceGraterThanProducts(price, menuProducts.getMenuProducts())) {
            throw new IllegalArgumentException("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 큽니다.");
        }
        this.id = UUID.randomUUID();
        this.menuGroup = menuGroup;
        this.menuGroupId = menuGroup.getId();
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuProducts = menuProducts.getMenuProducts();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public void changePrice(final Price menuPrice) {
        if (isMenuPriceGraterThanProducts(menuPrice, menuProducts)) {
            throw new IllegalArgumentException("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 큽니다.");
        }
        this.price = menuPrice;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void display() {
        if (isMenuPriceGraterThanProducts()) {
            throw new IllegalStateException("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 클 경우 메뉴를 노출할 수 없습니다.");
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public void hideIfMenuPriceGraterThanProducts() {
        if (isMenuPriceGraterThanProducts()) {
            this.displayed = false;
        }
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isMenuPriceGraterThanProducts(final Price menuPrice, final List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .map(menuProduct -> menuProduct.getProductPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .compareTo(menuPrice.getPrice()) < 0;
    }

    public boolean isMenuPriceGraterThanProducts() {
        return isMenuPriceGraterThanProducts(price, menuProducts);
    }

}
