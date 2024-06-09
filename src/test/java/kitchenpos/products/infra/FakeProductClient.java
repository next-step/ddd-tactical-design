package kitchenpos.products.infra;

import kitchenpos.menus.domain.tobe.menu.ProductClient;
import kitchenpos.products.domain.tobe.ProductRepository;

import java.util.List;
import java.util.UUID;

public class FakeProductClient implements ProductClient {
  private final ProductRepository productRepository;

  public FakeProductClient(final ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public int countProductIds(final List<UUID> productIds) {
    return productRepository.findAllByIdIn(productIds).size();
  }
}
