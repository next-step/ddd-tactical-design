package kitchenpos.menus.menu.tobe.domain.vo;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MenuSpecification {

    private final String name;
    private final Long price;
    private final UUID menuGroupId;
    private final Boolean displayed;
    private final List<MenuProductSpecification> menuProducts;

    public MenuSpecification(String name, Long price, UUID menuGroupId, Boolean displayed, List<MenuProductSpecification> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public Boolean getDisplayed() {
        return displayed;
    }

    public List<MenuProductSpecification> getMenuProducts() {
        return menuProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuSpecification that = (MenuSpecification) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(menuGroupId, that.menuGroupId) && Objects.equals(displayed, that.displayed) && Objects.equals(menuProducts, that.menuProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, menuGroupId, displayed, menuProducts);
    }
}
