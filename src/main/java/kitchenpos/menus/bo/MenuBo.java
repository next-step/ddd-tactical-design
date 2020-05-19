package kitchenpos.menus.bo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kitchenpos.common.model.Price;
import kitchenpos.menus.model.MenuCreateRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProductService;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MenuBo {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuProductService menuProductService;


    public MenuBo(
        MenuRepository menuRepository,
        MenuGroupRepository menuGroupRepository,
        MenuProductService menuProductService) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuProductService = menuProductService;
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

        Price menuProductsPriceSum = menu.computeMenuProductsPriceSum();
        if (Price.of(price).compareTo(menuProductsPriceSum) > 0) {
            throw new IllegalArgumentException();
        }

        menuRepository.save(menu);
        return menu;
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }

    private Menu map(MenuCreateRequest menuCreateRequest) {
        List<MenuProduct> menuProducts = menuCreateRequest.getMenuProducts().stream()
            .map(product -> menuProductService
                .getMenuProduct(product.getProductId(), product.getQuantity()))
            .collect(Collectors.toList());

        return new Menu(
            menuCreateRequest.getName(),
            menuCreateRequest.getPrice(),
            menuCreateRequest.getMenuGroupId(),
            new MenuProducts((menuProducts))
        );
    }
}
