package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.menus.tobe.application.MenuProducts;
import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.Name;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private Name name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

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

    @Transient
    private UUID menuGroupId;

    public Menu() {
    }


    public Menu(UUID uuid, Name name, Money price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        this.id = uuid;
        this.name = name;
        this.price = price.value();
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = new MenuProducts(menuProducts);
        this.menuGroupId = menuGroup.getId();
    }


    public void display() {
        if (price.compareTo(menuProducts.totalAmount().value()) > 0) {
            throw new IllegalArgumentException("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없습니다");
        }

        this.displayed = true;
    }


    public void hide() {
        this.displayed = false;
    }


    public void changePrice(Money toPrice) {
        if (toPrice.compareTo(menuProducts.totalAmount()) > 0) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
        }
        this.price = toPrice.value();
    }

    public void changeProductPrice(UUID productId, Money productPrice) {
        menuProducts.changePrice(productId, productPrice);

        if (price.compareTo(menuProducts.totalAmount().value()) > 0) {
           hide();
        }
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.values();
    }
}
