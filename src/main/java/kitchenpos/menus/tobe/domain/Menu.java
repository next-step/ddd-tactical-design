package kitchenpos.menus.tobe.domain;

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
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuDisplayedName name;

    @Embedded
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Embedded
    private MenuDisplayed displayed;

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

    public Menu() {
    }

    public Menu(MenuDisplayedName name, MenuGroup menuGroup, MenuPrice price, List<MenuProduct> menuProducts) {
        if(menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("상품은 1개 이상 등록해야 합니다.");
        }

        if(Objects.isNull(menuGroup)) {
            throw new IllegalArgumentException("메뉴는 메뉴 그룹에 속해야 합니다 ");
        }

        BigDecimal menuProductsPrice = menuProducts.stream().map(MenuProduct::calculatePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(price.value().compareTo(menuProductsPrice) > 0) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 합니다.");
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.menuGroup = menuGroup;
        this.menuGroupId = menuGroup.getId();
        this.price = price;
        this.menuProducts = menuProducts;
        this.displayed = new MenuDisplayed(Boolean.TRUE);
    }

    public UUID getId() {
        return id;
    }

    public MenuDisplayedName getName() {
        return name;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public MenuDisplayed getDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public BigDecimal calculateProductPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menuProducts) {
            sum = sum.add(menuProduct.calculatePrice());
        }
        return sum;
    }

    public void changePrice(long price) {
        this.changePrice(new MenuPrice(price));
    }

    public void changePrice(MenuPrice price) {
        this.price = price;
    }

    public void hide() {
        this.displayed.hide();
    }

    public void display() {
        BigDecimal menuProductsPrice = menuProducts.stream().map(MenuProduct::calculatePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(price.value().compareTo(menuProductsPrice) > 0) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 합니다.");
        }

        this.displayed.display();
    }
}
