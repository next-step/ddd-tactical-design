package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuGroup;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuName;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuVo;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuPurgomalumClient purgomalumClient;

    private final MenuPolicy menuPolicy;

    private final ProductContextService productContextService;

    public MenuServiceImpl(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final MenuPurgomalumClient purgomalumClient,
        final MenuPolicy menuPolicy,
        final ProductContextService productContextService
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
        this.menuPolicy = menuPolicy;
        this.productContextService = productContextService;
    }

    @Override
    public MenuVo.MenuInfo create(final MenuVo.Create request) {
        final MenuPrice price = request.price();
        final MenuName name = MenuName.of(request.name(), purgomalumClient);
        final MenuGroup menuGroup = getMenuGroup(request.menuGroupId());
        final List<MenuProduct> menuProducts = createMenuProducts(request.menuProducts());

        menuPolicy.validateMenuPrice(price, menuProducts);

        return MenuVo.MenuInfo.fromEntity(
            menuRepository.save(new Menu(
                UUID.randomUUID(),
                name,
                price,
                request.menuGroupId(),
                request.displayed(),
                menuProducts
            ))
        );
    }

    @Override
    public MenuVo.MenuInfo changePrice(final MenuVo.Update request) {
        final MenuPrice price = request.price();

        return menuPolicy.changePrice(request.menuId(), price);
    }

    @Override
    public MenuVo.MenuInfo display(final UUID menuId) {
        return menuPolicy.display(menuId);
    }

    @Override
    public MenuVo.MenuInfo hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.updateDisplayed(false);
        return MenuVo.MenuInfo.fromEntity(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuVo.MenuInfo> findAll() {
        return menuRepository.findAll()
            .stream()
            .map(MenuVo.MenuInfo::fromEntity)
            .toList();
    }

    private MenuGroup getMenuGroup(UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
            .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND_MENU_GROUP.toString()));
    }

    private List<MenuProduct> createMenuProducts(List<MenuProduct> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.NOT_FOUND_MENU_PRODUCT.toString());
        }

        final List<Product> products = productContextService.findAllByIds(
            menuProductRequests.stream().map(MenuProduct::getProductId).toList()
        );

        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException(ErrorCode.NOT_FOUND_ANY_PRODUCT.toString());
        }

        return menuProductRequests.stream()
            .map(request -> createMenuProduct(request, products))
            .toList();
    }

    private MenuProduct createMenuProduct(MenuProduct request, List<Product> products) {
        if (request.getQuantity() < 0) {
            throw new IllegalArgumentException(ErrorCode.PRODUCT_QTY_NOT_ALLOWED.toString());
        }

        final Product product = products.stream()
            .filter(p -> p.getId().equals(request.getProductId()))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND_PRODUCT.toString()));

        return new MenuProduct(product.getId(), request.getQuantity());
    }

}
