package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity(name = "TobeMenu")
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuInfo menuInfo;

    @Embedded
    private MenuGroupInfo menuGroupInfo;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {}

    public Menu(final MenuInfo menuInfo, final MenuGroupInfo menuGroupInfo, final MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.menuInfo = menuInfo;
        this.menuGroupInfo = menuGroupInfo;
        this.menuProducts = menuProducts;
        validateMenu();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return menuInfo.getName();
    }

    public BigDecimal getPrice() {
        return menuInfo.getMenuPrice().getPrice();
    }

    public MenuGroup getMenuGroup() {
        return menuGroupInfo.getMenuGroup();
    }

    public boolean isDisplayed() {
        return menuInfo.isDisplayed();
    }

    public List<MenuProduct> getMenuProducts() {
        return new ArrayList<>(menuProducts.getMenuProducts());
    }

    public UUID getMenuGroupId() {
        return menuGroupInfo.getMenuGroupId();
    }

    public void display() {
        if (!isValidPrice(menuInfo.getMenuPrice())) {
            throw new IllegalStateException("Menu의 가격은 MenuProducts의 금액의 합보다 적거나 같아야 보일 수 있습니다.");
        }
        menuInfo.display();
    }

    public void hide() {
        menuInfo.hide();
    }

    public void updateStatus() {
        if (!isValidPrice(menuInfo.getMenuPrice())) {
            menuInfo.hide();
        }
    }

    public void changePrice(final MenuPrice menuPrice) {
        if (!isValidPrice(menuPrice)) {
            throw new IllegalArgumentException("변경할 메뉴의 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }
        menuInfo.changePrice(menuPrice);
    }

    private boolean isValidPrice(final MenuPrice menuPrice) {
        return menuPrice.getPrice()
                .compareTo(menuProducts.sumProductPrice()) <= 0;
    }

    private void validateMenu() {
        if (!isValidPrice(menuInfo.getMenuPrice())) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }
    }
}
