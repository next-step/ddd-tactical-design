package kitchenpos.menu.ui.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.application.dto.MenuServiceRs;

public class MenuRs {
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean isDisplayed;
    private UUID menuGroupId;
    private List<MenuProductRs> menuProductRsList;

    public MenuRs(UUID id, String name, BigDecimal price, boolean isDisplayed, UUID menuGroupId,
                  List<MenuProductRs> menuProductRsList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isDisplayed = isDisplayed;
        this.menuGroupId = menuGroupId;
        this.menuProductRsList = menuProductRsList;
    }

    public MenuRs(MenuServiceRs rs) {
        this.id = rs.getId();
        this.name = rs.getName();
        this.price = rs.getPrice();
        this.isDisplayed = rs.isDisplayed();
        this.menuGroupId = rs.getMenuGroupId();
        this.menuProductRsList = rs.getMenuProductServiceRsList().stream()
                .map(mpsrs -> new MenuProductRs(mpsrs.getProductId(), mpsrs.getQuantity()))
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

    public List<MenuProductRs> getMenuProductRsList() {
        return menuProductRsList;
    }

    public static class MenuProductRs {
        private UUID productId;
        private long quantity;

        public MenuProductRs(UUID productId, long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public MenuProductRs() {
        }

        public UUID getProductId() {
            return productId;
        }

        public long getQuantity() {
            return quantity;
        }
    }
}
