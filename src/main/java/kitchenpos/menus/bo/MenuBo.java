package kitchenpos.menus.bo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kitchenpos.menus.model.MenuCreateRequest;
import kitchenpos.menus.model.MenuCreateRequest.MenuProduct;
import kitchenpos.menus.tobe.domain.group.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProductFactory;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.ProductQuantity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MenuBo {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuProductFactory menuProductFactory;

    public MenuBo(
        MenuRepository menuRepository,
        MenuGroupRepository menuGroupRepository,
        MenuProductFactory menuProductFactory) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuProductFactory = menuProductFactory;
    }

    @Transactional
    public Menu create(final MenuCreateRequest menuCreateRequest) {
        final BigDecimal price = menuCreateRequest.getPrice();

        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }

        if (!menuGroupRepository.existsById(menuCreateRequest.getMenuGroupId())) {
            throw new IllegalArgumentException();
        }

        Menu menu = map(menuCreateRequest);
        menuRepository.save(menu);
        return menu;
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }

    private Menu map(MenuCreateRequest menuCreateRequest) {
        return Menu.of(
            menuCreateRequest.getName(),
            menuCreateRequest.getPrice(),
            menuCreateRequest.getMenuGroupId(),
            map(menuCreateRequest.getMenuProducts())
        );
    }

    private MenuProducts map(List<MenuProduct> menuCreateRequest) {
        List<ProductQuantity> productQuantities = menuCreateRequest.stream()
            .map(menuProduct -> ProductQuantity
                .of(menuProduct.getProductId(), menuProduct.getQuantity()))
            .collect(Collectors.toList());

        return new MenuProducts(menuProductFactory.getMenuProducts(productQuantities));
    }
}
