package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.infra.MenuProductAntiCorruption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class MenuService {

    private MenuRepository menuRepository;
    private MenuGroupService menuGroupService;
    private MenuProductAntiCorruption menuProductAntiCorruption;

    public MenuService(
            MenuRepository menuRepository,
            MenuGroupService menuGroupService,
            MenuProductAntiCorruption menuProductAntiCorruption
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.menuProductAntiCorruption = menuProductAntiCorruption;
    }

    public Menu create(final Menu menu) {
        menu.validateByMenuGroup(menuGroupService.list());

        BigDecimal totalPrice = menuProductAntiCorruption.menuTotalPrice(menu.getMenuProducts());
        menu.validate(totalPrice);

        return menuRepository.save(menu);
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}
