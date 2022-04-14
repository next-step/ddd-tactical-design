package kitchenpos.menus.tobe.menu;

import java.util.List;
import java.util.UUID;

public class Menu {
    private final UUID id;
    private final MenuName name;
    private MenuPrice price;
    private boolean displayed;
    private List<MenuProduct> menuProducts;
    private UUID menuGroupId;

    public Menu(UUID id, MenuName name, MenuPrice price, boolean displayed, List<MenuProduct> menuProducts, UUID menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        setMenuProducts(menuProducts);
        setMenuGroupId(menuGroupId);
    }

    public Menu(UUID id, String name, long price, boolean displayed, List<MenuProduct> menuProducts, UUID menuGroupId) {
        this(id, new MenuName(name), new MenuPrice(price), displayed, menuProducts, menuGroupId);
    }

    public void changePrice(int price) {
        if (price > menuProductsAmount(menuProducts)) {
            this.displayed = false;
        }
        this.price = new MenuPrice(price);
    }

    public void display() {
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    private void setMenuProducts(List<MenuProduct> menuProducts) {
        if (menuProducts.isEmpty()) {
            throw new IllegalArgumentException("반드시 하나 이상의 메뉴 상품을 포함해야 합니다.");
        }
        if (this.price.isGreaterThan(menuProductsAmount(menuProducts))) {
            throw new IllegalArgumentException("가격은 메뉴상품 목록의 금액의 합보다 적거나 같아야 합니다.");
        }
        this.menuProducts = menuProducts;
    }

    private void setMenuGroupId(UUID menuGroupId) {
        if (menuGroupId == null) {
            throw new IllegalArgumentException("반드시 메뉴 그룹에 속해야 합니다.");
        }
        this.menuGroupId = menuGroupId;
    }

    private long menuProductsAmount(List<MenuProduct> menuProducts) {
        return menuProducts.stream().mapToLong(MenuProduct::amount).sum();
    }

    public MenuPrice getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
