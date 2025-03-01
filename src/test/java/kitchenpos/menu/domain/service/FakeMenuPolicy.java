package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.exception.MenuPriceInvalidException;
import kitchenpos.menu.domain.exception.MenuStateInvalidException;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuProducts;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.model.ProductId;

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
    public void hideMenu(ProductId productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);

        menus.forEach(menu -> {
            menu.updateDisplayed(false);
        });
    }

    @Override
    public MenuInfo changePrice(MenuId menuId, MenuPrice price) {
        var menu = getMenu(menuId);
        fakePriceValidation(exceptionStatus -> new MenuPriceInvalidException());
        return MenuInfo.fromEntity(menu);
    }

    @Override
    public MenuInfo display(MenuId menuId) {
        var menu = getMenu(menuId);
        fakePriceValidation(exceptionStatus -> new MenuStateInvalidException());
        return MenuInfo.fromEntity(menu);
    }

    @Override
    public void validateMenuPrice(MenuPrice price, MenuProducts menuProducts) {
        fakePriceValidation(exceptionStatus -> new MenuPriceInvalidException());
    }

    private void fakePriceValidation(Function<Boolean, RuntimeException> exceptionFunction) {
        if (exceptionStatus) {
            throw exceptionFunction.apply(exceptionStatus);
        }
    }

    private Menu getMenu(MenuId menuId) {
        return menuRepository.findByMenuId(menuId)
            .orElseThrow(NoSuchElementException::new);
    }
}

