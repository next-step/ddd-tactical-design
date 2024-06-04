package kitchenpos.menus.tobe.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.ProductProviderInterface;
import kitchenpos.menus.tobe.infra.dto.ProductConsumerDto;
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
  public MenuProduct findById(ProductConsumerDto productConsumerDto) {
    final Optional<Product> optProduct =
        productRepository.findById(productConsumerDto.getProductId());
    return MenuProduct.of(optProduct, productConsumerDto.getQuantity());
  }

  @Override
  public List<MenuProduct> findByIds(List<ProductConsumerDto> productConsumerDtos) {
    final List<MenuProduct> menuProducts = new ArrayList<>();

    for (ProductConsumerDto productConsumerDto : productConsumerDtos) {
      final Optional<Product> optProduct =
          productRepository.findById(productConsumerDto.getProductId());
      final MenuProduct menuProduct = MenuProduct.of(optProduct, productConsumerDto.getQuantity());
      menuProducts.add(menuProduct);
    }
    return menuProducts;
  }
}
