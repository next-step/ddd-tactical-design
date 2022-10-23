package kitchenpos.menus.menu.tobe.domain.vo;

import java.util.Objects;
import java.util.UUID;

public class MenuProductSpecification {

    private final UUID productId;
    private final Long quantity;

    public MenuProductSpecification(UUID productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductSpecification that = (MenuProductSpecification) o;
        return Objects.equals(productId, that.productId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }
}
