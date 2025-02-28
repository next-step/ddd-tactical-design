package kitchenpos.menu.domain.service;

import java.util.List;
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
        final MenuId menuId = MenuId.of(UUID.randomUUID());

        MenuProducts menuProducts = createMenuProducts(request.menuProducts().get(), menuId);

        menuPolicy.validateMenuPrice(price, menuProducts);

        return MenuVo.MenuInfo.fromEntity(
            menuRepository.save(new Menu(
                menuId,
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
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MENU.toString()));
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

    private MenuProducts createMenuProducts(List<MenuProduct> menuProductRequests, MenuId menuId) {

        productContextProvider.validateProduct(
            menuProductRequests.stream().map(MenuProduct::getProductId).toList(),
            menuProductRequests.size()
        );

        return MenuProducts.of(menuProductRequests.stream()
                            .map(request -> createMenuProduct(request, menuId))
                            .toList());
    }

    private MenuProduct createMenuProduct(MenuProduct request, MenuId menuId) {
        if (request.getQuantity().isNegative()) {
            throw new MenuProductQtyException();
        }

        return new MenuProduct(request.getProductId(), menuId, request.getQuantity());
    }

}
