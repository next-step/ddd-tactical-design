package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.menu.application.ProductContextProvider;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuGroup;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.exception.MenuProductQtyException;
import kitchenpos.menu.domain.model.MenuGroupId;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuName;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuProducts;
import kitchenpos.menu.domain.model.MenuVo;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultMenuService implements MenuQueryService, MenuCommandService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuPurgomalumClient purgomalumClient;

    private final MenuPolicy menuPolicy;

    private final ProductContextProvider productContextProvider;

    public DefaultMenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final MenuPurgomalumClient purgomalumClient,
        final MenuPolicy menuPolicy,
        final ProductContextProvider productContextProvider
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
        this.menuPolicy = menuPolicy;
        this.productContextProvider = productContextProvider;
    }

    @Override
    public MenuVo.MenuInfo create(final MenuVo.Create request) {
        final MenuPrice price = request.price();
        final MenuName name = MenuName.of(request.name(), purgomalumClient);
        final MenuGroup menuGroup = validateMenuGroup(request.menuGroupId());
        final MenuProducts menuProducts = createMenuProducts(request.menuProducts().get());

        menuPolicy.validateMenuPrice(price, menuProducts);

        return MenuVo.MenuInfo.fromEntity(
            menuRepository.save(new Menu(
                MenuId.of(UUID.randomUUID()),
                name,
                price,
                menuGroup.getMenuGroupId(),
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
    public MenuVo.MenuInfo display(final MenuId menuId) {
        return menuPolicy.display(menuId);
    }

    @Override
    public MenuVo.MenuInfo hide(final MenuId menuId) {
        final Menu menu = menuRepository.findByMenuId(menuId)
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

    private MenuGroup validateMenuGroup(MenuGroupId menuGroupId) {
        return menuGroupRepository.findByMenuGroupId(menuGroupId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MENU_GROUP.toString()));
    }

    private MenuProducts createMenuProducts(List<MenuProduct> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_MENU_PRODUCT.toString());
        }

        final List<Product> products = productContextProvider.findAllByProductIds(
            menuProductRequests.stream().map(MenuProduct::getProductId).toList()
        );

        if (products.size() != menuProductRequests.size()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_ANY_PRODUCT.toString());
        }

        return new MenuProducts(menuProductRequests.stream()
                                    .map(request -> createMenuProduct(request, products))
                                    .toList());
    }

    private MenuProduct createMenuProduct(MenuProduct request, List<Product> products) {
        if (request.getQuantity().isNegative()) {
            throw new MenuProductQtyException();
        }

        final Product product = products.stream()
            .filter(p -> p.getProductId().equals(request.getProductId()))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_PRODUCT.toString()));

        return new MenuProduct(product.getProductId(), request.getQuantity());
    }

}
