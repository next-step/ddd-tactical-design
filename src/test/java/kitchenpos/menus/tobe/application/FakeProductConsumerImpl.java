package kitchenpos.menus.tobe.application;

import java.util.ArrayList;
import java.util.List;
import kitchenpos.menus.tobe.MenuFixtures;
import kitchenpos.menus.application.dto.ProductConsumerDto;
import kitchenpos.menus.domain.menu.MenuProduct;
import kitchenpos.menus.domain.menu.ProductProviderInterface;
import kitchenpos.products.tobe.ProductFixtures;

public class FakeProductConsumerImpl implements ProductProviderInterface {
  @Override
  public MenuProduct findById(ProductConsumerDto productConsumerDto) {
    return MenuFixtures.menuProduct(ProductFixtures.product(), productConsumerDto.getQuantity());
  }

  @Override
  public List<MenuProduct> findByIds(List<ProductConsumerDto> productConsumerDtos) {
    final List<MenuProduct> menuProducts = new ArrayList<>();
    for (ProductConsumerDto productConsumerDto : productConsumerDtos) {
      menuProducts.add(
          MenuFixtures.menuProduct(ProductFixtures.product(), productConsumerDto.getQuantity()));
    }
    return menuProducts;
  }
}
