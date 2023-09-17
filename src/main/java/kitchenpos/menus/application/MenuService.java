package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.ui.dto.request.MenuChangePriceRequest;
import kitchenpos.menus.ui.dto.request.MenuCreateRequest;
import kitchenpos.menus.ui.dto.response.MenuResponse;
import kitchenpos.products.domain.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;
    private final MenuValidator validator;

    public MenuService(
            final MenuRepository menuRepository,
            final PurgomalumClient purgomalumClient,
            final MenuValidator validator
    ) {
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
        this.validator = validator;
    }

    @Transactional
    public MenuResponse create(final MenuCreateRequest request) {
        validator.validateMenuGroup(request.getMenuGroupId());
        validator.validateMenuProduct(request.getMenuProductCreateRequests());
        validator.validateMenuPrice(request.getMenuProductCreateRequests(), request.getPrice());
        final Menu menu = request.toMenu(purgomalumClient);
        Menu savedMenu = menuRepository.save(menu);
        return MenuResponse.from(savedMenu);
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        validator.validateChangePrice(menu.getMenuProducts(), request.getPrice());
        menu.changePrice(request.getPrice());
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        validator.validateChangePrice(menu.getMenuProducts(), menu.getPrice());
        menu.show();
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return MenuResponse.from(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return MenuResponse.from(menuRepository.findAll());
    }
}
