package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.ProductPriceResponse;
import kitchenpos.menus.tobe.domain.menu.domain.Menu;
import kitchenpos.menus.tobe.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MenuService {
    private final MenuRepository menuRepository;

    private final MenuGroupService menuGroupService;

    private final ProductService productService;

    public MenuService(final MenuRepository menuRepository, MenuGroupService menuGroupService, ProductService productService) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.productService = productService;
    }

    @Transactional
    public Menu create(final Menu menu) {
        if (!menuGroupService.existsMenuGroup(menu.getMenuGroupId())) {
            throw new IllegalArgumentException();
        }

        final Menu savedMenu = menuRepository.save(menu);
        List<ProductPriceResponse> prices = productService.findAllPrices(menu.getMenuProductIds());
        savedMenu.validateMenuPrice(prices);

        return savedMenu;
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}
