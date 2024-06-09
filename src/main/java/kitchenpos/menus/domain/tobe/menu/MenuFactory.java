package kitchenpos.menus.domain.tobe.menu;

import kitchenpos.common.domain.ProfanityValidator;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Component
public class MenuFactory {
  private final MenuGroupRepository menuGroupRepository;
  private final ProfanityValidator profanityValidator;
  private final ProductClient productClient;


  public MenuFactory(MenuGroupRepository menuGroupRepository, ProfanityValidator profanityValidator, ProductClient productClient) {
    this.menuGroupRepository = menuGroupRepository;
    this.profanityValidator = profanityValidator;
    this.productClient = productClient;
  }

  public Menu createMenu(UUID menuGroupId, List<MenuProduct> menuProducts, boolean displayed, String name, BigDecimal price){

    validateMenuGroup(menuGroupId);
    validateMenuProducts(menuProducts);

    MenuProducts createMenuProducts = createMenuProducts(menuProducts);

    return Menu.of(name, price, menuGroupId, displayed, createMenuProducts, profanityValidator);
  }

  private void validateMenuGroup(UUID groupId){
    menuGroupRepository.findById(groupId)
            .orElseThrow(NoSuchElementException::new);
  }

  private void validateMenuProducts(List<MenuProduct> menuProducts){
    if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
      throw new IllegalArgumentException();
    }

  }

  private MenuProducts createMenuProducts(List<MenuProduct> menuProductsList){

    return MenuProducts.of(productClient, menuProductsList);
  }
}
