package kitchenpos.products.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductPriceChangedEvent;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.PurgomalumClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final PurgomalumClient purgomalumClient;
  private final ApplicationEventPublisher applicationEventPublisher;

  public ProductService(
      final ProductRepository productRepository,
      final PurgomalumClient purgomalumClient,
      final ApplicationEventPublisher applicationEventPublisher) {
    this.productRepository = productRepository;
    this.purgomalumClient = purgomalumClient;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Transactional
  public ProductResponse create(final ProductRequest productRequest) {
    final Product product =
        productRepository.save(
            Product.create(productRequest.getName(), productRequest.getPrice(), purgomalumClient));
    return ProductResponse.of(product);
  }

  @Transactional
  public ProductResponse changePrice(final UUID productId, final ProductRequest productRequest) {
    final Product product =
        productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
    product.changePrice(productRequest.getPrice());
    this.menusProductPriceChanges(productId, productRequest.getPrice());

    return ProductResponse.of(product);
  }

  @Transactional(readOnly = true)
  public List<ProductResponse> findAll() {
    List<Product> products = productRepository.findAll();
    return products.stream().map(ProductResponse::of).toList();
  }

  private void menusProductPriceChanges(UUID productId, BigDecimal price) {
    applicationEventPublisher.publishEvent(new ProductPriceChangedEvent(this, productId, price));
  }
}
