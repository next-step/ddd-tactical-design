package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Function;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.exception.MenuPriceInvalidException;
import kitchenpos.menu.domain.exception.MenuStateInvalidException;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.menu.domain.repository.MenuRepository;

public class FakeMenuPolicy implements MenuPolicy {

    private final MenuRepository menuRepository;

    private Boolean exceptionStatus = false;

    public FakeMenuPolicy(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void setExceptionStatus(boolean condition) {
        this.exceptionStatus = condition;
    }

    @Override
    public void hideMenu(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);

        menus.forEach(menu -> {
            menu.updateDisplayed(false);
        });
    }

    @Override
    public MenuInfo changePrice(UUID menuId, MenuPrice price) {
        var menu = getMenu(menuId);
        fakePriceValidation(exceptionStatus -> new MenuPriceInvalidException());
        return MenuInfo.fromEntity(menu);
    }

    @Override
    public MenuInfo display(UUID menuId) {
        var menu = getMenu(menuId);
        fakePriceValidation(exceptionStatus -> new MenuStateInvalidException());
        return MenuInfo.fromEntity(menu);
    }

    @Override
    public void validateMenuPrice(MenuPrice price, List<MenuProduct> menuProducts) {
        fakePriceValidation(exceptionStatus -> new MenuPriceInvalidException());
    }

    private void fakePriceValidation(Function<Boolean, RuntimeException> exceptionFunction) {
        if (exceptionStatus) {
            throw exceptionFunction.apply(exceptionStatus);
        }
    }

    private Menu getMenu(UUID menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
    }
}

