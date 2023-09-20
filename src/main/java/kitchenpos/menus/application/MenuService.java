package kitchenpos.menus.application;

import kitchenpos.menus.shared.dto.MenuDto;
import kitchenpos.menus.shared.dto.MenuProductDto;
import kitchenpos.menus.shared.dto.request.MenuCreateRequest;
import kitchenpos.menus.shared.dto.request.MenuPriceChangeRequest;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductCreateService;
import kitchenpos.menus.tobe.domain.menu.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.ProductPriceService;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.shared.util.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductPriceService productPriceService;

    private final MenuProductCreateService menuProductCreateService;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductPriceService productPriceService,
        final MenuProductCreateService menuProductCreateService,
        final PurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productPriceService = productPriceService;
        this.menuProductCreateService = menuProductCreateService;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuDto create(final MenuCreateRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);
        final List<MenuProductDto> menuProductRequests = request.getMenuProducts();

        productPriceService.validateMenuProductDtoRequest(menuProductRequests);

        final List<MenuProduct> menuProducts = menuProductCreateService.getMenuProducts(menuProductRequests);

        final Menu menu = Menu.of(
                new MenuName(request.getName(), purgomalumClient),
                new MenuPrice(request.getPrice()),
                menuGroup,
                request.isDisplayed(),
                MenuProducts.from(menuProducts)
        );

        menuProductCreateService.valid(menu);
        return ConvertUtil.convert(menuRepository.save(menu), MenuDto.class);
    }

    @Transactional
    public MenuDto changePrice(final UUID menuId, final MenuPriceChangeRequest request) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.changeMenuPrice(new MenuPrice(request.getPrice()));

        menuProductCreateService.valid(menu);

        return ConvertUtil.convert(menu, MenuDto.class);
    }

    @Transactional
    public MenuDto display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);

        menuProductCreateService.valid(menu);

        menu.display();
        return ConvertUtil.convert(menu, MenuDto.class);
    }

    @Transactional
    public MenuDto hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.notDisplay();
        return ConvertUtil.convert(menu, MenuDto.class);
    }

    @Transactional(readOnly = true)
    public List<MenuDto> findAll() {
        return ConvertUtil.convertList(menuRepository.findAll(), MenuDto.class);
    }
}
