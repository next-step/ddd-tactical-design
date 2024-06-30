package kitchenpos.menus.tobe.application;

import kitchenpos.infra.PurgomalumClient;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.Name;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Menu create(final MenuCreateCommand request) {

        List<MenuProduct> menuProductRequests = request.menuProducts();

        Money price = new Money(request.price());
        MenuProducts menuProducts = MenuProducts.of(menuProductRequests, price);

        final MenuGroup menuGroup = menuGroupRepository.findById(request.menuGroupId())
                .orElseThrow(NoSuchElementException::new);

        final Menu menu = new Menu(UUID.randomUUID(), new Name(request.name(), purgomalumClient), price, menuGroup, request.displayed(), menuProducts.values());
        return menuRepository.save(menu);
    }


    @Transactional
    public Menu changePrice(final UUID menuId, final Menu request) {

        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);

        menu.changePrice(new Money(request.getPrice()));
        return menu;
    }


    @Transactional
    public Menu display(final UUID menuId) {

        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);

        menu.display();
        return menu;
    }


    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
