package kitchenpos.menus.infra;

import kitchenpos.menus.domain.tobe.menu.ProductClient;
import kitchenpos.products.domain.tobe.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class DefaultProductClient implements ProductClient {
  private final ProductRepository productRepository;

  public DefaultProductClient(final ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public int countProductIds(final List<UUID> productIds) {
    return productRepository.findAllByIdIn(productIds).size();
  }
}
