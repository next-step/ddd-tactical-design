package kitchenpos.products.tobe.domain.service;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.dto.ProductChangePriceDto;
import kitchenpos.products.tobe.domain.dto.ProductCreateDto;
import kitchenpos.products.tobe.domain.dto.ProductDto;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class ProductDomainService {
  private final ProductRepository productRepository;
  private final PurgomalumClient purgomalumClient;

  public ProductDomainService(ProductRepository productRepository, PurgomalumClient purgomalumClient) {
    this.productRepository = productRepository;
    this.purgomalumClient = purgomalumClient;
  }

  @Transactional
  public ProductDto create(final ProductCreateDto request) {
    boolean isProfanity = purgomalumClient.containsProfanity(request.getName());
    Product saved = productRepository.save(request.toEntity(isProfanity));
    return ProductDto.of(saved);
  }

  @Transactional
  public ProductDto changePrice(final ProductChangePriceDto request) {
    ProductPrice productPrice = request.getProductPrice();
    Product product = findOne(request.getId());
    product.changePrice(productPrice);

    return ProductDto.of(product);
  }

  @Transactional(readOnly = true)
  public Product findOne(final UUID id) {
    return productRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);
  }

  @Transactional(readOnly = true)
  public List<Product> findAll() {
    return productRepository.findAll();
  }
}
