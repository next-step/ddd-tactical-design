package kitchenpos.menus.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menugroup.model.MenuGroupV2;
import kitchenpos.menugroup.repository.MenuGroupRepositoryV2;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuPrice;
import kitchenpos.menus.tobe.domain.model.MenuProducts;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.dto.MenuRequest;
import kitchenpos.menus.tobe.infra.MenuProductsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuServiceV2 {

    private final MenuRepository menuRepository;
    private final MenuGroupRepositoryV2 menuGroupRepository;
    private final Profanities profanities;
    private final MenuProductsMapper menuProductsMapper;

    public MenuServiceV2(
        final MenuRepository menuRepository,
        final MenuGroupRepositoryV2 menuGroupRepository,
        final Profanities profanities,
        final MenuProductsMapper menuProductsMapper
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.profanities = profanities;
        this.menuProductsMapper = menuProductsMapper;
    }

    @Transactional
    public Menu create(final MenuRequest request) {
        final MenuGroupV2 menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);
        final DisplayedName displayedName = new DisplayedName(request.getName(), profanities);
        final MenuPrice price = new MenuPrice(request.getPrice());
        final MenuProducts menuProducts = menuProductsMapper.toMenuProducts(request.getMenuProducts());
        final Menu menu = new Menu(displayedName, price, menuGroup, true, menuProducts);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPrice price) {
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
