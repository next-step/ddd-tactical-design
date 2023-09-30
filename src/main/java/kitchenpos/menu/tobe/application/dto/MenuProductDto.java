package kitchenpos.menu.tobe.application.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.tobe.domain.MenuProduct;

public class MenuProductDto {

    private Long seq;

    private final UUID productId;

    private final long quantity;

    public MenuProductDto(Long seq, UUID productId, long quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static List<MenuProductDto> of(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
            .map(MenuProductDto::of)
            .collect(Collectors.toList());
    }

    public static MenuProductDto of(MenuProduct menuProduct) {
        return new MenuProductDto(menuProduct.getSeq(), menuProduct.getProductId(), menuProduct.getQuantity());
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
