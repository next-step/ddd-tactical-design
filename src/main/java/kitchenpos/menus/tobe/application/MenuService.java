package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupService menuGroupService;
    private final MenuProductService menuProductService;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupService menuGroupService,
            final MenuProductService menuProductService
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.menuProductService = menuProductService;
    }

    public Menu create(final Menu menu) {
        validate(menu);
        return menuRepository.save(menu);
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }

    private void validate(final Menu menu) {
        menuGroupService.existsMenuGroupById(menu.getMenuGroupId());

        BigDecimal totalProductsPrice = menuProductService.getTotalMenuProductsPrice(menu.getMenuProducts());
        menu.priceValidate(totalProductsPrice);
    }
}
