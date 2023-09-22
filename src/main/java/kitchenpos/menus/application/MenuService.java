package kitchenpos.menus.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                request.getPrice(),
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
    private MenuProducts createMenuProducts(List<MenuProduct> menuProductsRequest) {
        List<ProductDto> products = productApiService.findAllByIdIn(menuProductsRequest.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList()));

        return new MenuProducts(menuProductsRequest.stream()
                .map(menuProduct ->
                        new MenuProduct(
                                menuProduct.getProductId(),
                                menuProduct.getQuantity(),
                                getPrice(products, menuProduct)
                        )
                )
                .collect(Collectors.toList())
        );
    }

    private Price getPrice(List<ProductDto> products, MenuProduct menuProduct) {
        return products.stream()
                .filter(productDto -> productDto.getProductId().equals(menuProduct.getProductId()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new)
                .getPrice();
    }

//    @Transactional
//    public Menu changePrice(final UUID menuId, final Menu request) {
//        final BigDecimal price = request.getPrice();
//        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
//            throw new IllegalArgumentException();
//        }
//        final Menu menu = menuRepository.findById(menuId)
//            .orElseThrow(NoSuchElementException::new);
//        BigDecimal sum = BigDecimal.ZERO;
//        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
//            sum = sum.add(
//                menuProduct.getProduct()
//                    .getPrice()
//                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
//            );
//        }
//        if (price.compareTo(sum) > 0) {
//            throw new IllegalArgumentException();
//        }
//        menu.setPrice(price);
//        return menu;
//    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);

        menu.display(menuPricePolicy);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
