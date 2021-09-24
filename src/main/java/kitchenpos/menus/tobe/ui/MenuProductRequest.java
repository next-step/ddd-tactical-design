package kitchenpos.menus.tobe.ui;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProductId;
import kitchenpos.common.domain.Quantity;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductSeq;

public class MenuProductRequest {

    private final Long seq;
    private final UUID productId;
    private final BigDecimal price;
    private final Long quantity;

    public MenuProductRequest(
        final Long seq,
        final UUID productId,
        final BigDecimal price,
        final Long quantity
    ) {
        this.seq = seq;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public MenuProductSeq getSeq() {
        return new MenuProductSeq(this.seq);
    }

    public ProductId getProductId() {
        return new ProductId(this.productId);
    }

    public Price getPrice() {
        return new Price(this.price);
    }

    public Quantity getQuantity() {
        return new Quantity(this.quantity);
    }

    public MenuProduct toEntity() {
        return new MenuProduct(getSeq(), getProductId(), getPrice(), getQuantity());
    }
}
