package kitchenpos.menus.tobe.domain.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class MenuCreateCommand {
    private BigDecimal price;
    private UUID menuGroupId;
    private String name;
    private boolean displayed;
    private Map<UUID, Long> productQuantityMap;

    public MenuCreateCommand(BigDecimal price, UUID menuGroupId, String name, boolean displayed, Map<UUID, Long> productQuantityMap) {
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.name = name;
        this.displayed = displayed;
        this.productQuantityMap = productQuantityMap;
    }

    public static MenuCreateCommand create(BigDecimal price, UUID menuGroupId, String name, boolean displayed, Map<UUID, Long> productQuantityMap) {
        return new MenuCreateCommand(price, menuGroupId, name, displayed, productQuantityMap);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public String getName() {
        return name;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public Map<UUID, Long> getProductQuantityMap() {
        return productQuantityMap;
    }
}
