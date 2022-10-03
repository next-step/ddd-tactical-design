package kitchenpos.menus.domain.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MenuRequest {
    private String name;
    private BigDecimal price;
    private UUID menuGroupId;

    private boolean displayed;
    private List<MenuProductRequest> menuProducts;

    protected MenuRequest() {
    }

    public MenuRequest(String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductRequest> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
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

    public List<MenuProductRequest> getMenuProducts() {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public static class MenuProductRequest {
        private UUID productId;
        private long quantity;

        protected MenuProductRequest() {
        }

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
    }

}
