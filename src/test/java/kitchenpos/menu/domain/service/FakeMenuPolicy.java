package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.fixture.MenuFixture;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.menu.domain.repository.MenuRepository;

public class FakeMenuPolicy implements MenuPolicy {

    private final MenuRepository menuRepository;

    public FakeMenuPolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void hideMenu(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);

        menus.forEach(menu -> {
            menu.setDisplayed(false);
            menuRepository.save(menu);
        });
    }

    @Override
    public MenuInfo changePrice(UUID menuId, MenuPrice price) {
        return MenuInfo.fromEntity(MenuFixture.init().toEntity());
    }

    @Override
    public MenuInfo display(UUID menuId) {
        throw new IllegalStateException();
    }

    @Override
    public void validateMenuPrice(MenuPrice price, List<MenuProduct> menuProducts) {

    }
}

