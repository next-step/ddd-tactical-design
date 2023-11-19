package kitchenpos.menu.tobe.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.tobe.domain.Menu;

public class DisplayMenuResponse {

    private final UUID id;
    private final String name;
    private final BigDecimal price;
    private final UUID menuGroupId;
    private final List<MenuProductDto> menuProducts;
    private final boolean displayed;

    public DisplayMenuResponse(UUID id, String name, BigDecimal price, UUID menuGroupId, List<MenuProductDto> menuProducts, boolean displayed) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    public static DisplayMenuResponse of(Menu entity) {
        return new DisplayMenuResponse(entity.getId(), entity.getNameValue(), entity.getPrice(), entity.getMenuGroupId(),
            MenuProductDto.of(entity.getMenuProducts().getValue()), entity.isDisplayed());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<MenuProductDto> getMenuProducts() {
        return menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
