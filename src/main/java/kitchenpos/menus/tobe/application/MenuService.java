package kitchenpos.menus.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.service.MenuDomainService;
import kitchenpos.menus.tobe.ui.dto.MenuRequest;
import kitchenpos.menus.tobe.ui.dto.MenuRequest.MenuProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuDomainService menuDomainService;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuDomainService menuDomainService
    ) {
        this.menuRepository = menuRepository;
        this.menuDomainService = menuDomainService;
    }

    @Transactional
    public Menu create(final MenuRequest.Create request) {
        final List<MenuProductRequest> menuProductRequests = request.getMenuProductRequests();
        validateMenuProductRequest(menuProductRequests);

        final List<MenuProduct> menuProducts = menuProductRequests.stream()
                .map(MenuProductRequest::toEntity)
                .collect(Collectors.toList());

        final Menu menu = new Menu(request.getDisplayName()
                , request.getMenuPrice()
                , request.isDisplayed()
                , request.getMenugroupId()
                , menuProducts
                , menuDomainService);

        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuRequest.ChangePrice request) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.changePrice(request.getPrice(), menuDomainService);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.display(menuDomainService);
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

    private void validateMenuProductRequest(List<MenuProductRequest> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.size() == 0) {
            throw new IllegalArgumentException();
        }
    }
}
