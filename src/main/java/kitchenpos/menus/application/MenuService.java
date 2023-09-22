package kitchenpos.menus.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menus.application.dto.ChangeMenuPriceRequest;
import kitchenpos.menus.application.dto.CreateMenuRequest;
import kitchenpos.menus.application.dto.MenuProductDto;
import kitchenpos.menus.application.dto.ProductDto;
import kitchenpos.menus.domain.DisplayedName;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuPricePolicy;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuProducts;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.Price;
import kitchenpos.menus.domain.PurgomalumClient;
import kitchenpos.menus.domain.Quantity;
import kitchenpos.menus.infra.DefaultProductApiService;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final PurgomalumClient purgomalumClient;
    private final MenuPricePolicy menuPricePolicy;
    private final DefaultProductApiService productApiService;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final PurgomalumClient purgomalumClient,
            final MenuPricePolicy menuPricePolicy,
            final DefaultProductApiService productApiService) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
        this.menuPricePolicy = menuPricePolicy;
        this.productApiService = productApiService;
    }

    @Transactional
    public Menu create(final CreateMenuRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);

        final MenuProducts menuProducts = createMenuProducts(request.getMenuProducts());

        final Menu menu = new Menu(
                UUID.randomUUID(),
                new DisplayedName(request.getName(), purgomalumClient),
                new Price(request.getPrice()),
                menuGroup,
                menuProducts,
                menuPricePolicy
        );
        return menuRepository.save(menu);
    }

    /**
     * menuProductsRequest로 MenuProducts를 만든다.
     * - menuProductsRequest의 productId가 모두 존재하는지 확인한다. (getPrice)
     */
    private MenuProducts createMenuProducts(List<MenuProductDto> menuProductsRequest) {
        if (menuProductsRequest == null) {
            throw new IllegalArgumentException();
        }
        List<ProductDto> products = productApiService.findAllByIdIn(menuProductsRequest.stream()
                .map(MenuProductDto::getProductId)
                .collect(Collectors.toList()));

        return new MenuProducts(menuProductsRequest.stream()
                .map(menuProduct -> createMenuProduct(products, menuProduct))
                .collect(Collectors.toList())
        );
    }

    private MenuProduct createMenuProduct(List<ProductDto> products, MenuProductDto menuProduct) {
        UUID productId = menuProduct.getProductId();
        ProductDto productDto = findProductDto(products, productId);
        return new MenuProduct(
                productId,
                new Quantity(menuProduct.getQuantity()),
                new Price(productDto.getPrice())
        );
    }

    private ProductDto findProductDto(List<ProductDto> products, UUID productId) {
        return products.stream()
                .filter(productDto -> productDto.getProductId().equals(productId))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangeMenuPriceRequest request) {
        final Menu menu = findById(menuId);
        menu.changePrice(new Price(request.getPrice()), menuPricePolicy);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.display(menuPricePolicy);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    private Menu findById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
