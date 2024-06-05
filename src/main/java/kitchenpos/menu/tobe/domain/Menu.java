package kitchenpos.menu.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.exception.CanNotChangeDisplay;
import kitchenpos.exception.IllegalPriceException;
import kitchenpos.menuGroup.tobe.domain.MenuGroup;
import kitchenpos.menuproduct.tobe.domain.MenuProducts;

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

//    @ManyToOne
//    @JoinColumn(
//            name = "menu_group_id",
//            columnDefinition = "binary(16)",
//            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
//    )
    private MenuGroup menuGroup;
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean menuDisplayStatus;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(MenuName menuName, MenuPrice menuPrice, MenuGroup menuGroup, boolean menuDisplayStatus, MenuProducts menuProducts) {
        validatePrice(menuPrice);

        validateMenuProductPrice(menuPrice.getPrice().compareTo(menuProducts.getTotalPrice()) > 0, new IllegalPriceException("메뉴상품의 총 가격을 초과할 수 없습니다.", menuPrice.getPrice()));
        this.id = UUID.randomUUID();
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuGroup = menuGroup;
        this.menuDisplayStatus = menuDisplayStatus;
        this.menuProducts = menuProducts;
    }

    private static void validateMenuProductPrice(boolean menuPrice, IllegalPriceException exception) {
        if (menuPrice) {
            throw exception;
        }
    }

    private static void validatePrice(MenuPrice menuPrice) {
        if (Objects.isNull(menuPrice)) {
            throw new IllegalPriceException("가격정보는 필수로 입력해야 합니다.");
        }
        if (menuPrice.getPrice() < 0) {
            throw new IllegalPriceException("가격은 0원 미만일 수 없습니다. ", menuPrice.getPrice());
        }

    }

    public void changeMenuPrice(Long newPrice) {
        validateMenuProductPrice(newPrice.compareTo(menuProducts.getTotalPrice()) > 0, new IllegalPriceException("메뉴상품의 총 가격을 초과할 수 없습니다.", newPrice));
        this.menuPrice = new MenuPrice(newPrice);
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

    public MenuGroup getMenuGroup() {
        return menuGroup;
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
