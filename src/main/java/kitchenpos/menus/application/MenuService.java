package kitchenpos.menus.application;

import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuDetailResponse;
import kitchenpos.menus.dto.MenuProductElement;
import kitchenpos.menus.mapper.MenuMapper;
import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.products.event.ProductPriceChangedEvent;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.support.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toUnmodifiableList;
import static kitchenpos.menus.mapper.MenuMapper.toMenuDetailResponse;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public MenuService(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository, ProductRepository productRepository, PurgomalumClient purgomalumClient) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuDetailResponse create(final MenuCreateRequest request) {
        final List<MenuProductElement> menuProductRequests = request.getMenuProducts();

        List<Product> products = productRepository.findAllByIdIn(menuProductRequests.parallelStream()
                .map(MenuProductElement::getProductId)
                .collect(toUnmodifiableList()));

        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException("메뉴 상품에 존재하지 않는 상품이 포함되어 있습니다.");
        }

        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(() -> new NoSuchElementException("메뉴 그룹이 존재하지 않습니다."));

        final List<MenuProduct> menuProducts = new ArrayList<>();
        for (MenuProductElement menuProductElement : menuProductRequests) {
            final Product product = productRepository.findById(menuProductElement.getProductId())
                    .orElseThrow(NoSuchElementException::new);
            menuProducts.add(MenuProduct.create(
                    ProductInMenu.create(product.getId()),
                    menuProductElement.getQuantity()
            ));
        }

        final Menu menu = Menu.create(
                MenuName.create(request.getName(), purgomalumClient),
                MenuPrice.create(request.getPrice()),
                menuGroup,
                MenuDisplay.create(request.isDisplayed()),
                menuProducts
        );

        if (isMenuPriceLowerThanMenuProductPriceSum(menu)) {
            throw new IllegalArgumentException("메뉴 가격은 메뉴 상품 가격의 합보다 작거나 같아야 합니다.");
        }
        return toMenuDetailResponse(menuRepository.save(menu));
    }

    @Transactional
    public MenuDetailResponse changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final Menu menu = getMenuById(menuId);
        menu.changePrice(MenuPrice.create(request.getPrice()));
        if (isMenuPriceLowerThanMenuProductPriceSum(menu)) {
            throw new IllegalArgumentException("메뉴 가격은 메뉴 상품 가격의 합보다 작거나 같아야 합니다.");
        }
        return toMenuDetailResponse(menu);
    }

    @Transactional
    public MenuDetailResponse display(final UUID menuId) {
        final Menu menu = getMenuById(menuId);
        menu.display();
        if (isMenuPriceLowerThanMenuProductPriceSum(menu)) {
            throw new IllegalStateException("메뉴 가격은 메뉴 상품 가격의 합보다 크면 메뉴를 노출할 수 없습니다.");
        }
        return toMenuDetailResponse(menu);
    }

    @Transactional
    public MenuDetailResponse hide(final UUID menuId) {
        final Menu menu = getMenuById(menuId);
        menu.hide();
        return toMenuDetailResponse(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuDetailResponse> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(MenuMapper::toMenuDetailResponse)
                .collect(toUnmodifiableList());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleProductPriceChangedEvent(ProductPriceChangedEvent event) {
        List<Menu> menus = findAllByProductId(event.getId());
        checkMenuCouldBeDisplayedAfterProductPriceChanged(menus);
    }

    public List<Menu> findAllByProductId(UUID productId) {
        return menuRepository.findAllByProductId(productId);
    }

    private Map<UUID, Product> getProductsInMenu(Menu menu) {
        final List<UUID> productIds = menu.getMenuProducts()
                .parallelStream()
                .map(menuProduct -> menuProduct.getProduct().getId())
                .collect(toUnmodifiableList());

        return productRepository.findAllByIdIn(productIds)
                .parallelStream()
                .collect(Collectors.toMap(Product::getId, product -> product));
    }

    private boolean isMenuPriceLowerThanMenuProductPriceSum(Menu menu) {
        final Map<UUID, Product> productsInMenu = getProductsInMenu(menu);
        if (isNull(productsInMenu)) {
            return true;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                    BigDecimal.valueOf(menuProduct.getQuantity())
                            .multiply(productsInMenu.get(menuProduct.getProduct().getId()).getPrice())
            );
        }

        return menu.getPrice().compareTo(sum) > 0;
    }

    private void checkMenuCouldBeDisplayedAfterProductPriceChanged(List<Menu> menus) {
        menus.forEach(menu -> {
            final List<UUID> productIds = menu.getMenuProducts().stream().map(menuProduct -> menuProduct.getProduct().getId()).collect(toUnmodifiableList());
            final List<Product> products = productRepository.findAllByIdIn(productIds);
            final BigDecimal totalMenuProductPrice = products.parallelStream()
                    .map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (isMenuPriceGreaterThanSumOfMenuProducts(menu, totalMenuProductPrice)) {
                menu.hide();
            }
        });
    }

    private boolean isMenuPriceGreaterThanSumOfMenuProducts(Menu menu, BigDecimal totalMenuProductPrice) {
        return menu.getPrice().compareTo(totalMenuProductPrice) > 0;
    }


    private Menu getMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다."));
    }
}
