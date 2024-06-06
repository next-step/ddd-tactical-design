package kitchenpos.menu.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.exception.CanNotChangeDisplay;
import kitchenpos.exception.IllegalPriceException;

import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName menuName;

    @Embedded
    private MenuPrice menuPrice;

    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean menuDisplayStatus;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public static Menu of(MenuName menuName, MenuPrice menuPrice, UUID menuGroupId, boolean menuDisplayStatus, MenuProducts menuProducts) {
        validatePrice(menuPrice);
        validateMenuProductPrice(menuPrice, menuProducts);
        return new Menu(menuName, menuPrice, menuGroupId, menuDisplayStatus, menuProducts);
    }

    private Menu(MenuName menuName, MenuPrice menuPrice, UUID menuGroupId, boolean menuDisplayStatus, MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuGroupId = menuGroupId;
        this.menuDisplayStatus = menuDisplayStatus;
        this.menuProducts = menuProducts;
    }

    private static void validateMenuProductPrice(MenuPrice menuPrice, MenuProducts menuProducts) {
        if (menuPrice.getPrice().compareTo(menuProducts.getTotalPrice()) > 0) {
            throw new IllegalPriceException("메뉴상품의 총 가격을 초과할 수 없습니다.", menuPrice.getPrice());
        }
    }

    private static void validatePrice(MenuPrice menuPrice) {
        if (Objects.isNull(menuPrice)) {
            throw new IllegalPriceException("가격정보는 필수로 입력해야 합니다.");
        }
    }

    public void changeMenuPrice(Long newPrice) {
        validateMenuProductPrice(newPrice);
        this.menuPrice = new MenuPrice(newPrice);
    }

    private void validateMenuProductPrice(Long newPrice) {
        if (newPrice.compareTo(menuProducts.getTotalPrice()) > 0) {
            throw new IllegalPriceException("메뉴상품의 총 가격을 초과할 수 없습니다.", newPrice);
        }
    }

    public void changeMenuDisplayStatus(boolean newStatus) {
        if (newStatus && (menuProducts.getTotalPrice() > this.menuPrice.getPrice())) {
            throw new CanNotChangeDisplay("메뉴 가격이 메뉴상품 가격을 초과해 노출시킬 수 없습니다.");
        }
        this.menuDisplayStatus = newStatus;
    }


    public UUID getId() {
        return id;
    }

    public String getMenuName() {
        return menuName.getName();
    }

    public Long getMenuPrice() {
        return menuPrice.getPrice();
    }

    public UUID getMenuGroup() {
        return menuGroupId;
    }

    public boolean isMenuDisplayStatus() {
        return menuDisplayStatus;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
