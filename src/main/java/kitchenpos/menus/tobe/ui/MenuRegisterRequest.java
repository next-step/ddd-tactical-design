package kitchenpos.menus.tobe.ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProductId;
import kitchenpos.menus.tobe.domain.menu.Displayed;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;

public class MenuRegisterRequest {

    private final String displayedName;
    private final BigDecimal price;
    private final boolean displayed;
    private final UUID menuGroupId;
    private final List<MenuProductRequest> menuProductRequests;

    public MenuRegisterRequest(
        final String displayedName,
        final BigDecimal menuPrice,
        final boolean menuDisplayed,
        final UUID menuGroupId,
        final List<MenuProductRequest> menuProducts
    ) {
        this.displayedName = displayedName;
        this.price = menuPrice;
        this.displayed = menuDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuProductRequests = menuProducts;
    }

    public String getDisplayedName() {
        return this.displayedName;
    }

    public Price getPrice() {
        return new Price(this.price);
    }

    public Displayed isDisplayed() {
        return new Displayed(this.displayed);
    }

    public MenuGroupId getMenuGroupId() {
        return new MenuGroupId(this.menuGroupId);
    }

    public List<MenuProduct> getMenuProducts() {
        return this.menuProductRequests
            .stream()
            .map(MenuProductRequest::toEntity)
            .collect(Collectors.toList());
    }

    public List<ProductId> getProductIds() {
        return this.menuProductRequests
            .stream()
            .map(MenuProductRequest::getProductId)
            .collect(Collectors.toList());
    }
}
