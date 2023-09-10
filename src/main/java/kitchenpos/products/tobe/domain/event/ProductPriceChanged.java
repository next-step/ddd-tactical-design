package kitchenpos.products.tobe.domain.event;

import static org.apache.commons.lang3.ObjectUtils.requireNonEmpty;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class ProductPriceChanged extends ApplicationEvent {

    private final UUID productId;
    private final BigDecimal changedPrice;


    public ProductPriceChanged(UUID productId, BigDecimal changedPrice) {
        super(ProductPriceChanged.class);

        this.productId = requireNonEmpty(productId, "ProductPriceChanged Event는 ProductId를 가지고 있어야 합니다");
        this.changedPrice = requireNonEmpty(changedPrice, "ProductPriceChanged Event는 변경될 가격을 가지고 있어야 합니다");
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getChangedPrice() {
        return changedPrice;
    }
}
