package kitchenpos.products.tobe.domain.event;

import java.util.UUID;

public class ProductPriceChangeEvent {

  private final UUID id;

  public ProductPriceChangeEvent(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }
}
