package kitchenpos.products.infra;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class ProductPriceChangesEvent extends ApplicationEvent {
  private final UUID productId;
  private final BigDecimal productPrice;

  public ProductPriceChangesEvent(
      final Object source, final UUID productId, final BigDecimal productPrice) {
    super(source);
    this.productId = productId;
    this.productPrice = productPrice;
  }

  public UUID getProductId() {
    return productId;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }
}
