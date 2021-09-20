package kitchenpos.menus.tobe.ui.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.domain.model.MenuProduct;

public class MenuRequest {

    public static class Create {
        private String displayName;
        private UUID menugroupId;
        private BigDecimal menuPrice;
        private List<MenuProductRequest> menuProductRequests;

        private boolean displayed;

        public Create(String displayName, UUID menugroupId, BigDecimal menuPrice,
                List<MenuProductRequest> menuProductRequests, boolean displayed) {
            this.displayName = displayName;
            this.menugroupId = menugroupId;
            this.menuPrice = menuPrice;
            this.menuProductRequests = menuProductRequests;
            this.displayed = displayed;
        }

        public String getDisplayName() {
            return displayName;
        }

        public UUID getMenugroupId() {
            return menugroupId;
        }

        public BigDecimal getMenuPrice() {
            return menuPrice;
        }

        public List<UUID> getProductIds() {
            return getMenuProductRequests().stream()
                    .map(MenuProductRequest::getProductId)
                    .collect(Collectors.toList());
        }

        public List<MenuProductRequest> getMenuProductRequests() {
            return menuProductRequests;
        }

        public boolean isDisplayed() {
            return displayed;
        }
    }

    public static class MenuProductRequest {
        private UUID productId;
        private long quantity;

        public MenuProductRequest(UUID productId, long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public UUID getProductId() {
            return productId;
        }

        public long getQuantity() {
            return quantity;
        }

        public MenuProduct toEntity() {
            return new MenuProduct(productId, quantity);
        }
    }

    public static class ChangePrice {
        private BigDecimal price;

        public ChangePrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}
