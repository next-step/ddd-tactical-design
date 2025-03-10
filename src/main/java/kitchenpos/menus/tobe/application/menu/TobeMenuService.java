package kitchenpos.menus.tobe.application.menu;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.application.menugroup.TobeMenuGroupService;
import kitchenpos.menus.tobe.domain.ProductPriceService;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.dto.menu.MenuChangeRequest;
import kitchenpos.menus.tobe.dto.menu.MenuCreateRequest;
import kitchenpos.menus.tobe.dto.menu.MenuResponse;
import kitchenpos.shared.client.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TobeMenuService {

    private final TobeMenuRepository menuRepository;
    private final TobeMenuGroupService tobeMenuGroupService;
    private final PurgomalumClient purgomalumClient;
    private final ProductPriceService productPriceService;

    public TobeMenuService(
        final TobeMenuRepository menuRepository,
        final TobeMenuGroupService tobeMenuGroupService,
        final PurgomalumClient purgomalumClient,
        final ProductPriceService productPriceService
    ) {
        this.menuRepository = menuRepository;
        this.tobeMenuGroupService = tobeMenuGroupService;
        this.purgomalumClient = purgomalumClient;
        this.productPriceService = productPriceService;
    }

    @Transactional(readOnly = true)
    public Menu getById(UUID menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + menuId));
    }

    @Transactional
    public MenuResponse create(final MenuCreateRequest request) {
        final MenuGroup menuGroup = tobeMenuGroupService.getById(request.menuGroupId());
        Menu menu = request.toEntityWith(purgomalumClient, menuGroup, productPriceService);
        return MenuResponse.from(menuRepository.save(menu));
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuChangeRequest request) {
        final Menu menu = getById(menuId);
        menu.changePrice(request.price(), productPriceService);
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = getById(menuId);
        menu.display(productPriceService);
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = getById(menuId);
        menu.hide();
        return MenuResponse.from(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return menuRepository.findAll().stream().map(MenuResponse::from).toList();
    }
}
