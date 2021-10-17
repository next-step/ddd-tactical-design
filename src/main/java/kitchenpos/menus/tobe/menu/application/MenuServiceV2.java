package kitchenpos.menus.tobe.menu.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.menus.tobe.menu.domain.model.Menu;
import kitchenpos.menus.tobe.menu.domain.model.MenuPrice;
import kitchenpos.menus.tobe.menu.domain.model.MenuProducts;
import kitchenpos.menus.tobe.menu.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.menu.dto.MenuRequest;
import kitchenpos.menus.tobe.menu.infra.MenuProductsMapper;
import kitchenpos.menus.tobe.menugroup.domain.repository.MenuGroupRepositoryV2;
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
        menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(() -> new NoSuchElementException("해당 메뉴그룹을 찾을 수 없습니다."));
        final DisplayedName displayedName = new DisplayedName(request.getName(), profanities);
        final MenuPrice price = new MenuPrice(request.getPrice());
        final MenuProducts menuProducts = menuProductsMapper.toMenuProducts(request.getMenuProducts());
        final Menu menu = new Menu(displayedName, price, request.getMenuGroupId(), true, menuProducts);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPrice price) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new NoSuchElementException("해당 메뉴를 찾을 수 없습니다."));
        return menu.changePrice(price);
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new NoSuchElementException("해당 메뉴를 찾을 수 없습니다."));
        return menu.display();
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new NoSuchElementException("해당 메뉴를 찾을 수 없습니다."));
        return menu.hide();
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
