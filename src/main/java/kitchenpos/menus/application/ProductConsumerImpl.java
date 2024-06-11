package kitchenpos.menus.application;

import java.util.ArrayList;
import java.util.List;

import kitchenpos.menus.application.dto.ProductConsumerDto;
import kitchenpos.menus.domain.menu.ProductProviderInterface;
import kitchenpos.menus.domain.menu.MenuProduct;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumerImpl implements ProductProviderInterface {
  private final ProductRepository productRepository;

  public ProductConsumerImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public MenuProduct findById(final ProductConsumerDto productConsumerDto) {
    final Product product =
        productRepository
            .findById(productConsumerDto.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

    return MenuProduct.of(product.getId(), productConsumerDto.getQuantity(), product.getPrice());
  }

  @Override
  public List<MenuProduct> findByIds(final List<ProductConsumerDto> productConsumerDtos) {
    final List<MenuProduct> menuProducts = new ArrayList<>();

    for (ProductConsumerDto productConsumerDto : productConsumerDtos) {
      final Product product =
          productRepository
              .findById(productConsumerDto.getProductId())
              .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
      final MenuProduct menuProduct =
          MenuProduct.of(product.getId(), productConsumerDto.getQuantity(), product.getPrice());
      menuProducts.add(menuProduct);
    }
    return menuProducts;
  }
}
