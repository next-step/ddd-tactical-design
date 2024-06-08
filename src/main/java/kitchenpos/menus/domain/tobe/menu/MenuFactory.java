package kitchenpos.menus.domain.tobe.menu;

import kitchenpos.common.domain.ProfanityValidator;
import kitchenpos.menus.application.dto.MenuProductRequest;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroupRepository;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Component
public class MenuFactory {
  private final MenuGroupRepository menuGroupRepository;
  private final ProductRepository productRepository;
  private final ProfanityValidator profanityValidator;

  public MenuFactory(MenuGroupRepository menuGroupRepository, ProductRepository productRepository, ProfanityValidator profanityValidator) {
    this.menuGroupRepository = menuGroupRepository;
    this.productRepository = productRepository;
    this.profanityValidator = profanityValidator;
  }

  public Menu createMenu(UUID menuGroupId, List<MenuProductRequest> menuProducts, boolean displayed, String name, BigDecimal price){

    validateMenuGroup(menuGroupId);
    validateMenuProducts(menuProducts);

    MenuProducts createMenuProducts = createMenuProducts(menuProducts);

    return Menu.of(name, price, menuGroupId, displayed, createMenuProducts, profanityValidator);
  }

  private void validateMenuGroup(UUID groupId){
    final MenuGroup menuGroup = menuGroupRepository.findById(groupId)
            .orElseThrow(NoSuchElementException::new);
  }

  private void validateMenuProducts(List<MenuProductRequest> menuProductRequests){
    if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
      throw new IllegalArgumentException();
    }
  }

  private MenuProducts createMenuProducts(List<MenuProductRequest> menuProductRequests){
    final List<Product> products = productRepository.findAllByIdIn(
            menuProductRequests.stream()
                    .map(MenuProductRequest::getProductId)
                    .toList()
    );
    if (products.size() != menuProductRequests.size()) {
      throw new IllegalArgumentException();
    }
    final MenuProducts menuProducts = MenuProducts.of();

    for (final MenuProductRequest menuProductRequest : menuProductRequests) {
      Product product = menuProductRequest.getProduct();
      final MenuProduct menuProduct = MenuProduct.of(menuProductRequest.getProductId(),
              product.getProductPrice(),
              Long.valueOf(menuProductRequest.getQuantity()));
      menuProducts.add(menuProduct);
    }

    return menuProducts;
  }
}
