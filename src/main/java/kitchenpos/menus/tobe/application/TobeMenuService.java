package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.infra.MenuTranslator;
import kitchenpos.menus.tobe.ui.MenuForm;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TobeMenuService {
    private final TobeMenuRepository menuRepository;
    private final TobeMenuGroupRepository menuGroupRepository;
    private final MenuTranslator menuTranslator;
    private final TobePurgomalumClient purgomalumClient;

    public TobeMenuService(
        final TobeMenuRepository menuRepository,
        final TobeMenuGroupRepository menuGroupRepository,
        final MenuTranslator menuTranslator,
        final TobePurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuTranslator = menuTranslator;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuForm create(final MenuForm request) {
        final MenuPrice menuPrice = new MenuPrice(request.getPrice());

        final TobeMenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        List<TobeMenuProduct> tobeProducts = menuTranslator.productFindAllByIdIn(request.getMenuProducts());

        final TobeMenu menu = new TobeMenu(
            new MenuName(request.getName(), purgomalumClient),
            menuPrice,
            menuGroup,
            request.isDisplayed(),
            new MenuProducts(tobeProducts)
        );

        return MenuForm.of(menuRepository.save(menu));
    }

    @Transactional
    public MenuForm changePrice(final UUID menuId, final MenuForm request) {
        final MenuPrice menuPrice = new MenuPrice(request.getPrice());
        final TobeMenu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);

        menu.changePrice(menuPrice);
        return MenuForm.of(menu);
    }

    @Transactional
    public MenuForm display(final UUID menuId) {
        final TobeMenu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.display();
        return MenuForm.of(menu);
    }

    @Transactional
    public MenuForm hide(final UUID menuId) {
        final TobeMenu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return MenuForm.of(menu);
    }

    @Transactional
    public void updateDisplayedStutus(final UUID menuId) {
        final TobeMenu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);

        menu.updateDisplayedStatus();
    }

    @Transactional(readOnly = true)
    public List<MenuForm> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(MenuForm::of)
                .collect(Collectors.toList());
    }
}
