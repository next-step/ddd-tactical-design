package kitchenpos.menus.tobe.ui.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {
    private String name;
    private BigDecimal price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductCreateRequest> menuProductCreateRequest;

    public MenuCreateRequest() {
    }

    public MenuCreateRequest(String name, BigDecimal price, UUID menuGroupId, boolean displayed,
                             List<MenuProductCreateRequest> menuProductCreateRequest) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProductCreateRequest = menuProductCreateRequest;
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

    public List<MenuProductCreateRequest> getMenuProductCreateRequest() {
        return menuProductCreateRequest;
    }

    public static class MenuProductCreateRequest {
        private UUID productId;
        private Long quantity;

        public MenuProductCreateRequest() {
        }

        public MenuProductCreateRequest(UUID productId, Long quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public UUID getProductId() {
            return productId;
        }

        public Long getQuantity() {
            return quantity;
        }

    }
}
