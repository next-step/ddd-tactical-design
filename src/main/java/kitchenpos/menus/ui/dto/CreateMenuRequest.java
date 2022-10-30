package kitchenpos.menus.ui.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CreateMenuRequest {
    public BigDecimal price;
    public UUID menuGroupId;
    public String name;
    public boolean isDisplayed;
    public List<CreateMenuProductRequest> createMenuProductRequests = new ArrayList<>();

    public static class CreateMenuProductRequest {
        public UUID productId;
        public long quantity;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CreateMenuProductRequest that = (CreateMenuProductRequest) o;
            return quantity == that.quantity && Objects.equals(productId, that.productId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, quantity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateMenuRequest that = (CreateMenuRequest) o;
        return isDisplayed == that.isDisplayed && Objects.equals(price, that.price) && Objects.equals(menuGroupId, that.menuGroupId) && Objects.equals(name, that.name) && Objects.equals(createMenuProductRequests, that.createMenuProductRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, menuGroupId, name, isDisplayed, createMenuProductRequests);
    }
}
