package kitchenpos.menu.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CreateMenuServiceRq {
    private String name;
    private BigDecimal price;
    private boolean isDisplayed;
    private UUID menuGroupId;
    private List<MenuProductServiceRq> menuProductServiceRqs;

    public CreateMenuServiceRq(String name, BigDecimal price, boolean isDisplayed, UUID menuGroupId,
                               List<MenuProductServiceRq> menuProductServiceRqs) {
        this.name = name;
        this.price = price;
        this.isDisplayed = isDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuProductServiceRqs = menuProductServiceRqs;
    }

    public CreateMenuServiceRq() {
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductServiceRq> getMenuProductDtos() {
        return menuProductServiceRqs;
    }

    public static class MenuProductServiceRq {
        private UUID productId;
        private long quantity;

        public MenuProductServiceRq(UUID productId, long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public MenuProductServiceRq() {
        }

        public UUID getProductId() {
            return productId;
        }

        public long getQuantity() {
            return quantity;
        }
    }
}
