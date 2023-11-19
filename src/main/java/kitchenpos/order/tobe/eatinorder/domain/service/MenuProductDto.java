package kitchenpos.order.tobe.eatinorder.domain.service;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menu.tobe.domain.MenuProduct;
import kitchenpos.menu.tobe.domain.MenuProducts;

public class MenuProductDto {

    private Long seq;

    private BigDecimal price;

    private UUID productId;

    private long quantity;

    public MenuProductDto(Long seq, BigDecimal price, UUID productId, long quantity) {
        this.seq = seq;
        this.price = price;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static MenuProductDto from(MenuProduct menuProduct) {
        return new MenuProductDto(menuProduct.getSeq(), menuProduct.getPrice().getValue(), menuProduct.getProductId(), menuProduct.getQuantity());
    }
}
