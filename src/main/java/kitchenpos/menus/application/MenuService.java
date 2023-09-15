package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.ui.request.MenuChangePriceRequest;
import kitchenpos.menus.ui.request.MenuCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuPurgomalumClient menuPurgomalumClient;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final MenuPurgomalumClient menuPurgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuPurgomalumClient = menuPurgomalumClient;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {

        List<MenuProduct> menuProducts = Objects.isNull(request.getMenuProductCreateRequests()) ? null : request.getMenuProductCreateRequests()
                .stream()
                .map(menuProductCreateRequests -> new MenuProduct(menuProductCreateRequests.getProductId(),
                        menuProductCreateRequests.getPrice(),
                        menuProductCreateRequests.getQuantity()))
                .collect(Collectors.toList());

        MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId()).orElseThrow(NoSuchElementException::new);

        Menu menu = new Menu(request.getName(), menuPurgomalumClient, request.getPrice(), menuGroup, request.isDisplayed(), menuProducts);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.changePrice(request.getPrice());
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
