package kitchenpos.product.tobe.domain.port.in;

import kitchenpos.product.tobe.domain.Price;

import java.util.UUID;

public class NewProductPriceChangerCommand {
    private final UUID productId;
    private final Price price;

    public NewProductPriceChangerCommand(UUID productId, Price price) {
        this.productId = productId;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public Price getPrice() {
        return price;
    }
}
