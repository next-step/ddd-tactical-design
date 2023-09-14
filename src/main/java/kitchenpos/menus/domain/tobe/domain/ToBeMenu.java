package kitchenpos.menus.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tobe_menu")
@Entity
public class ToBeMenu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName displayedName;

    @Embedded
    private MenuPrice price;

    @Column(name = "menu_group_id")
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private ToBeMenuProducts menuProducts;

    protected ToBeMenu() {
    }

    public ToBeMenu(MenuName name, BigDecimal price, UUID menuGroupId, boolean displayed,
        ToBeMenuProducts menuProducts) {

        validationOfMenuGroup(menuGroupId);
        validationOfPrice(price, menuProducts);

        this.id = UUID.randomUUID();
        this.displayedName = name;
        this.price = MenuPrice.of(price);
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void changePrice(BigDecimal price) {
        validationOfPrice(price);
        this.price = MenuPrice.of(price);
    }

    public void display() {
        validationOfPrice();
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    private void validationOfPrice(BigDecimal price, ToBeMenuProducts menuProducts) {
        validationOfPrice(MenuPrice.of(price), menuProducts.sumOfProducts());
    }

    private void validationOfPrice(BigDecimal price) {
        validationOfPrice(MenuPrice.of(price), menuProducts.sumOfProducts());
    }

    private void validationOfPrice() {
        validationOfPrice(price, menuProducts.sumOfProducts());
    }

    private void validationOfPrice(MenuPrice price, BigDecimal sumOfProducts) {
        if (price.isGreaterThan(sumOfProducts)) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.");
        }
    }

    private void validationOfMenuGroup(UUID menuGroup) {
        if (menuGroup == null) {
            throw new IllegalArgumentException("메뉴는 특정 메뉴 그룹에 속해야 한다.");
        }
    }

    public void changeProductPrice(UUID productId, BigDecimal productPrice) {
        menuProducts.changePrice(productId, productPrice);
        if (price.isGreaterThan(menuProducts.sumOfProducts())) {
            hide();
        }
    }
}
