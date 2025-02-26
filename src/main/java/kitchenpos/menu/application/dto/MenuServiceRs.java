package kitchenpos.menu.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;

public class MenuServiceRs {
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean isDisplayed;
    private UUID menuGroupId;
    private List<MenuProductServiceRs> menuProductServiceRsList;

    public MenuServiceRs(UUID id, String name, BigDecimal price, boolean isDisplayed, UUID menuGroupId,
                         List<MenuProductServiceRs> menuProductServiceRsList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isDisplayed = isDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuProductServiceRsList = menuProductServiceRsList;
    }

    public MenuServiceRs(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getInnerName();
        this.price = menu.getInnerPrice();
        this.isDisplayed = menu.isDisplayed();
        this.menuGroupId = menu.getMenuGroupId();
        this.menuProductServiceRsList = menu.getMenuProducts().stream()
                .map(mp -> new MenuProductServiceRs(mp.getProductId(), mp.getInnerQuantity()))
                .toList();
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

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductServiceRs> getMenuProductServiceRsList() {
        return menuProductServiceRsList;
    }

    public static class MenuProductServiceRs {
        private UUID productId;
        private long quantity;

        public MenuProductServiceRs(UUID productId, long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public MenuProductServiceRs() {
        }

        public UUID getProductId() {
            return productId;
        }

        public long getQuantity() {
            return quantity;
        }
    }
}
