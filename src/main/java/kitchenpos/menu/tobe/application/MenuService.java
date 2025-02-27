package kitchenpos.menu.tobe.application;

import kitchenpos.common.exception.MenuException;
import kitchenpos.common.exception.MenuNotFoundException;
import kitchenpos.common.tobe.Profanities;
import kitchenpos.menu.tobe.application.dto.request.MenuCreateRequest;
import kitchenpos.menu.tobe.application.dto.request.MenuPriceChangeRequest;
import kitchenpos.menu.tobe.domain.menu.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static kitchenpos.common.exception.ErrorCode.*;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final Profanities profanities;
    private final MenuValidator menuValidator;
    private final ProductClient productClient;

    public MenuService(
            final MenuRepository menuRepository,
            final Profanities profanities,
            final MenuValidator menuValidator,
            final ProductClient productClient
    ) {
        this.menuRepository = menuRepository;
        this.profanities = profanities;
        this.menuValidator = menuValidator;
        this.productClient = productClient;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) throws MenuException {
        return menuRepository.save(Menu.of(
                MenuName.from(request.name(), profanities),
                MenuPrice.from(request.price()),
                request.menuGroupId(),
                MenuDisplayStatus.from(request.displayed()),
                MenuProducts.from(request.menuProducts()),
                menuValidator,
                productClient
        ));
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPriceChangeRequest request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND));
        menu.changeMenuPrice(request.price(), productClient);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() ->
                        new MenuNotFoundException(MENU_NOT_FOUND)
                );
        menu.show(productClient);
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