package kitchenpos.menus.application;

import kitchenpos.common.domain.Purgomalum;
import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.util.CollectionUtils;
import kitchenpos.common.values.Name;
import kitchenpos.common.values.Price;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.dto.ChangePriceMenuRequest;
import kitchenpos.menus.dto.CreateMenuProductRequest;
import kitchenpos.menus.dto.CreateMenuRequest;
import kitchenpos.menus.dto.MenuDto;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.ProductValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.common.exception.KitchenPosExceptionType.BAD_REQUEST;
import static kitchenpos.common.exception.KitchenPosExceptionType.NOT_FOUND;

@Service
public class MenuService {
    private final MenuValidator menuValidator;
    private final ProductValidator productValidator;
    private final MenuProductService menuProductService;
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final Purgomalum purgomalum;

    public MenuService(
            final MenuValidator menuValidator,
            final ProductValidator productValidator,
            final MenuProductService menuProductService,
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final Purgomalum purgomalum
    ) {
        this.menuValidator = menuValidator;
        this.productValidator = productValidator;
        this.menuProductService = menuProductService;
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalum = purgomalum;
    }

    @Transactional
    public MenuDto create(final CreateMenuRequest request) {
        List<CreateMenuProductRequest> menuProductRequests = request.getMenuProducts();
        CollectionUtils.requireNonEmpty(menuProductRequests,new KitchenPosException("메뉴 구성 상품이 없으므로", BAD_REQUEST));
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(() -> new KitchenPosException("요청하신 ID에 해당하는 메뉴 그룹을", NOT_FOUND));

        final Price price = new Price(request.getPrice());

        productValidator.isExistProducts(
                menuProductRequests.stream()
                        .map(CreateMenuProductRequest::getProductId)
                        .collect(Collectors.toList())
        );

        final List<MenuProduct> menuProducts = menuProductService.create(menuProductRequests);
        menuValidator.validatePrice(price, menuProducts);
        final Name name = new Name(request.getName(), purgomalum);
        final Menu menu = new Menu(name, price, menuGroup.getId(), menuProducts, request.isDisplayed());
        Menu result = menuRepository.save(menu);
        return MenuDto.from(result);
    }

    @Transactional
    public MenuDto changePrice(final UUID menuId, final ChangePriceMenuRequest request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new KitchenPosException("요청하신 ID에 해당하는 메뉴를", NOT_FOUND));

        Price price = new Price(request.getPrice());
        menuValidator.validatePrice(price, menu.getMenuProducts());
        menu.changePrice(price);
        return MenuDto.from(menu);
    }

    @Transactional
    public MenuDto display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new KitchenPosException("요청하신 ID에 해당하는 메뉴를", NOT_FOUND));
        menuValidator.validatePrice(menu.getPrice(), menu.getMenuProducts());
        menu.display();
        return MenuDto.from(menu);
    }

    @Transactional
    public MenuDto hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new KitchenPosException("요청하신 ID에 해당하는 메뉴를", NOT_FOUND));
        menu.hide();
        return MenuDto.from(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuDto> findAll() {
        return menuRepository.findAll().stream()
                .map(MenuDto::from)
                .collect(Collectors.toList());
    }
}
