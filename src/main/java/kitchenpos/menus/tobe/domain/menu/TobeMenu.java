package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class TobeMenu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName menuName;

    @Embedded
    private MenuPrice price;

    @Column(name = "menu_group_id")
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private TobeMenuProducts tobeMenuProducts = new TobeMenuProducts();


    protected TobeMenu() {
    }

    public TobeMenu(UUID id, MenuName menuName, MenuPrice price, UUID menuGroupId, boolean displayed,
                    List<TobeMenuProduct> tobeMenuProducts) {
        this(id, menuName, price, menuGroupId, displayed, new TobeMenuProducts(tobeMenuProducts));
    }

    public TobeMenu(UUID id, MenuName menuName, MenuPrice price, UUID menuGroupId, boolean displayed,
                    TobeMenuProducts tobeMenuProducts) {
        if (Objects.isNull(tobeMenuProducts) || tobeMenuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴의 상품은 비어있을 수 없습니다.");
        }
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.tobeMenuProducts = tobeMenuProducts;

    }

    public void display() {
        this.checkSum(price);
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public void updatePrice(MenuPrice price) {
        this.checkSum(price);
        this.price = price;
    }

    private void checkSum(final MenuPrice menuPrice) {
        MenuPrice sum = tobeMenuProducts.sum();
        if (menuPrice.greaterThan(sum)) {
            throw new IllegalArgumentException(
                    "메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 합니다. " + "price: " + menuPrice + " sum: " + sum);
        }
    }

    public UUID getId() {
        return id;
    }
    public MenuName getMenuName() {
        return menuName;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public TobeMenuProducts getTobeMenuProducts() {
        return tobeMenuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
