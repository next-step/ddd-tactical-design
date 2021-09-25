package kitchenpos.menus.tobe.ui.dto;

import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.tobe.domain.Amount;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.Name;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuCreateRequest {
    private final String name;
    private final BigDecimal price;
    private final UUID menuGroupId;
    private final boolean displayed;
    private final List<MenuProductRequest> menuProducts;

    public MenuCreateRequest(final String name, final BigDecimal price, final UUID menuGroupId, final boolean displayed, final List<MenuProductRequest> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Name getName(Profanities profanities) {
        return new Name(name, profanities);
    }

    public Amount getAmount() {
        return new Amount(price);
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public MenuProducts getMenuProducts() {
        return new MenuProducts(Optional.ofNullable(menuProducts)
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .map(MenuProductRequest::toEntity)
                .collect(Collectors.toList()));
    }
}
