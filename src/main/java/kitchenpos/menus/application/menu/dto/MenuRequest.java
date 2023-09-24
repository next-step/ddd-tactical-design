package kitchenpos.menus.application.menu.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MenuRequest {
    private final String name;

    private final BigDecimal price;

    private final UUID menuGroupId;

    private final boolean displayed;

    private final List<MenuProductRequest> menuProductsRequests; // 일급 컬렉션

    public MenuRequest(final String name, final BigDecimal price, final UUID menuGroupId, final boolean displayed, final List<MenuProductRequest> menuProductRequests) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProductsRequests = menuProductRequests;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductRequest> getMenuProductsRequests() {
        return Optional.ofNullable(menuProductsRequests)
                .orElse(Collections.emptyList());
    }
}
