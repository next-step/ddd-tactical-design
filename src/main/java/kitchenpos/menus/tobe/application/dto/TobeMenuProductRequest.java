package kitchenpos.menus.tobe.application.dto;

import kitchenpos.menus.tobe.domain.menuproduct.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct;
import kitchenpos.products.tobe.domain.TobeProduct;

import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TobeMenuProductRequest {
    private final UUID productId;
    private final long quantity;

    public TobeMenuProductRequest(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static List<TobeMenuProductRequest> from(List<TobeMenuProduct> tobeMenuProducts) {
        return tobeMenuProducts.stream()
                               .map(it -> new TobeMenuProductRequest(it.getProductId(), it.getQuantity().getQuantity()))
                               .collect(Collectors.toList());
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
