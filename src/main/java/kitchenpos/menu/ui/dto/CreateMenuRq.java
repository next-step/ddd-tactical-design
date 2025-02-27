package kitchenpos.menu.ui.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.application.dto.CreateMenuServiceRq;
import kitchenpos.menu.application.dto.CreateMenuServiceRq.MenuProductServiceRq;

public class CreateMenuRq {
    private String name;
    private BigDecimal price;
    private boolean isDisplayed;
    private UUID menuGroupId;
    private List<MenuProductRq> menuProductRqs;

    public CreateMenuRq(String name, BigDecimal price, boolean isDisplayed, UUID menuGroupId,
                        List<MenuProductRq> menuProductRqs) {
        this.name = name;
        this.price = price;
        this.isDisplayed = isDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuProductRqs = menuProductRqs;
    }

    public CreateMenuRq() {
    }

    public CreateMenuServiceRq toServiceRq() {
        return new CreateMenuServiceRq(
                this.name,
                this.price,
                this.isDisplayed,
                this.menuGroupId,
                this.menuProductRqs.stream()
                        .map(mp -> new MenuProductServiceRq(mp.productId, mp.quantity))
                        .toList()
        );
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

    public List<MenuProductRq> getMenuProductRqs() {
        return menuProductRqs;
    }

    public static class MenuProductRq {
        private UUID productId;
        private long quantity;

        public MenuProductRq(UUID productId, long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public MenuProductRq() {
        }

        public UUID getProductId() {
            return productId;
        }

        public long getQuantity() {
            return quantity;
        }
    }
}
