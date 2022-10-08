package kitchenpos.menus.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.domain.*;
import kitchenpos.menus.exception.MenuGroupNotFoundException;
import kitchenpos.menus.exception.MenuNotFoundException;
import kitchenpos.menus.ui.request.MenuCreateRequest;
import kitchenpos.menus.ui.request.MenuPriceChangeRequest;
import kitchenpos.menus.ui.request.MenuProductCreateRequest;
import kitchenpos.menus.ui.response.MenuResponse;
import kitchenpos.profanity.infra.ProfanityCheckClient;
import kitchenpos.reader.ProductPriceReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProfanityCheckClient profanityCheckClient;
    private final ProductPriceReader productPriceReader;

    public MenuService(
        MenuRepository menuRepository,
        MenuGroupRepository menuGroupRepository,
        ProfanityCheckClient profanityCheckClient,
        ProductPriceReader productPriceReader
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.profanityCheckClient = profanityCheckClient;
        this.productPriceReader = productPriceReader;
    }

    @Transactional
    public MenuResponse create(final MenuCreateRequest request) {
        MenuName menuName = new MenuName(request.getName(), profanityCheckClient);
        MenuPrice menuPrice = new MenuPrice(request.getPrice());
        MenuGroup menuGroup = findMenuGroupById(request.getMenuGroupId());
        MenuProducts menuProducts = mapToMenuProducts(request.getMenuProductCreateRequests());
        Menu menu = new Menu(menuName, menuPrice, menuGroup, menuProducts);

        return MenuResponse.from(menuRepository.save(menu));
    }

    private MenuGroup findMenuGroupById(UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
            .orElseThrow(() -> new MenuGroupNotFoundException("ID 에 해당하는 메뉴그룹이 없습니다."));
    }

    private MenuProducts mapToMenuProducts(List<MenuProductCreateRequest> requests) {
        List<MenuProduct> menuProducts = requests.stream()
            .map(this::mapToMenuProduct)
            .collect(Collectors.toList());
        return new MenuProducts(menuProducts);
    }

    private MenuProduct mapToMenuProduct(MenuProductCreateRequest request) {
        UUID productId = request.getProductId();
        long quantity = request.getQuantity();

        return new MenuProduct(
            productId,
            new MenuProductPrice(productPriceReader.getProductPriceById(productId)),
            new MenuProductQuantity(quantity)
        );
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final MenuPriceChangeRequest request) {
        Menu menu = findMenuById(menuId);
        MenuPrice newPrice = new MenuPrice(request.getPrice());
        menu.changePrice(newPrice);
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        Menu menu = findMenuById(menuId);
        menu.display();
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        Menu menu = findMenuById(menuId);
        menu.hide();
        return MenuResponse.from(menu);
    }

    private Menu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(() -> new MenuNotFoundException("ID 에 해당하는 메뉴가 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll() {
        return MenuResponse.of(menuRepository.findAll());
    }
}
