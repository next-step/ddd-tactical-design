package kitchenpos.menu.tobe.application;

import kitchenpos.common.exception.MenuException;
import kitchenpos.common.exception.MenuNotFoundException;
import kitchenpos.common.tobe.Profanities;
import kitchenpos.menu.tobe.domain.menu.*;
import kitchenpos.menu.tobe.domain.menugroup.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static kitchenpos.common.exception.ErrorCode.*;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final Profanities profanities;
    private final MenuValidator menuValidator;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final Profanities profanities,
            final MenuValidator menuValidator
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.profanities = profanities;
        this.menuValidator = menuValidator;
    }

    @Transactional
    public Menu create(final Menu request) throws MenuException {
        menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(() -> new MenuException(MENU_GROUP_NOT_FOUND));

        return menuRepository.save(Menu.of(
                request.getName(),
                request.getMenuPrice(),
                request.getMenuGroupId(),
                request.getMenuProducts(),
                request.isDisplayed(),
                profanities,
                menuValidator
        ));
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final Menu request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND));
        menu.changeMenuPrice(request.getMenuPrice(), menuValidator);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() ->
                        new MenuNotFoundException(MENU_NOT_FOUND)
                );
        menu.show(menuValidator);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND));
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
