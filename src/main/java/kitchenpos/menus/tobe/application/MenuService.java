package kitchenpos.menus.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Price;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuGroup;
import kitchenpos.menus.tobe.domain.model.MenuProducts;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.dto.MenuRequestDto;
import kitchenpos.menus.tobe.infra.MenuProductsTranslator;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tobeMenuService")
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final PurgomalumClient purgomalumClient;
    private final MenuProductsTranslator menuProductsTranslator;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final PurgomalumClient purgomalumClient,
        final MenuProductsTranslator menuProductsTranslator
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
        this.menuProductsTranslator = menuProductsTranslator;
    }

    @Transactional
    public Menu create(final MenuRequestDto request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);
        final DisplayedName displayedName = new DisplayedName(request.getName(), purgomalumClient);
        final Price price = new Price(request.getPrice());
        final MenuProducts menuProducts = menuProductsTranslator.getMenuProducts(request.getMenuProducts());
        final Menu menu = new Menu(displayedName, price, menuGroup, true, menuProducts);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final Price price) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        return menu.changePrice(price);
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        return menu.display();
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        return menu.hide();
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
