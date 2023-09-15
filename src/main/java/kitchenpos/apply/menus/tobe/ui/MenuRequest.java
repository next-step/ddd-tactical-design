package kitchenpos.apply.menus.tobe.ui;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuRequest {
    @NotNull
    private final UUID id;

    @NotEmpty
    private final String name;

    @NotNull
    private final BigDecimal price;

    @NotNull
    private final UUID menuGroupId;

    private final boolean displayed;

    @NotNull
    private final List<MenuProductRequest> menuProductRequests;

    public MenuRequest(String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductRequest> menuProductRequests) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProductRequests = menuProductRequests;
    }

    public MenuRequest(UUID id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductRequest> menuProductRequests) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProductRequests = menuProductRequests;
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

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductRequest> getMenuProductRequests() {
        return menuProductRequests;
    }

}
