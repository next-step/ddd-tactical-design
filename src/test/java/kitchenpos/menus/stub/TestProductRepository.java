package kitchenpos.menus.stub;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import kitchenpos.menus.tobe.domain.product.DisplayedName;
import kitchenpos.menus.tobe.domain.product.Product;
import kitchenpos.menus.tobe.domain.product.ProductRepository;

public class TestProductRepository implements ProductRepository {

  private final Map<Long, Product> mapRepository = new HashMap<>();

  public TestProductRepository(Long productId, String productName, BigDecimal productPrice) {
    Product product = new Product(new DisplayedName(productName, false), productPrice);
    mapRepository.put(productId, product);
  }

  @Override
  public Optional<Product> findById(Long id) {
    return Optional.ofNullable(mapRepository.get(id));
  }
}
