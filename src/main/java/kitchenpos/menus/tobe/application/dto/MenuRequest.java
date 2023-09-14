package kitchenpos.menus.tobe.application.dto;

import kitchenpos.menus.tobe.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class MenuRequest {
    private String name;

    private BigDecimal price;

    private boolean displayed;

    private List<MenuProduct> menuProducts;

    private UUID menuGroupId;

    public MenuRequest(String name, BigDecimal price, boolean displayed, UUID menuGroupId, List<MenuProduct> menuProducts) {
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public void validate(final Predicate<String> predicate) {
        validatePrice();
        validateMenuProductRequests();
        validateName(predicate);
    }

    public void validatePrice() {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public void validateMenuProductRequests() {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public void validateName(final Predicate<String> predicate) {
        if (Objects.isNull(name) || predicate.test(name)) {
            throw new IllegalArgumentException();
        }
    }
}
