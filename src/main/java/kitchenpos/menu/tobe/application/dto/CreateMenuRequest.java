package kitchenpos.menu.tobe.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateMenuRequest {

    private final String name;
    private final BigDecimal price;
    private final UUID menuGroupId;

    @NotEmpty
    @NotNull
    private final List<CreateMenuProductRequest> menuProducts;
    private final boolean displayed;

    public CreateMenuRequest(String name, BigDecimal price, UUID menuGroupId, List<CreateMenuProductRequest> menuProducts, boolean displayed) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
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

    public List<CreateMenuProductRequest> getMenuProducts() {
        return menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
