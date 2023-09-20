package kitchenpos.menus.application;


import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuName;
import kitchenpos.menus.tobe.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.event.ProductPriceChangedEvent;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Menu create(final Menu request) {
        MenuPrice menuPrice = new MenuPrice(request.getPrice());
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);
        final MenuProducts menuProducts = new MenuProducts(request.getMenuProducts());
        final List<Product> products = productRepository.findAllByIdIn(
            menuProducts.getList().stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList())
        );
        menuProducts.checkEqualsSize(products.size());

        BigDecimal productSumPridce = BigDecimal.ZERO;
        for (final MenuProduct menuProductRequest : menuProducts.getList()) {
            final Product product = productRepository.findById(menuProductRequest.getProductId())
                .orElseThrow(NoSuchElementException::new);
            productSumPridce = productSumPridce.add(
                menuProductRequest.getAmount(product.getPrice()));
        }
        if (menuPrice.getValue().compareTo(productSumPridce) > 0) {
            throw new IllegalArgumentException();
        }
        return menuRepository.save(request);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final Menu request) {
        MenuPrice menuPrice = new MenuPrice(request.getPrice());
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal productSumPrice = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            final Product product = productRepository.findById(menuProduct.getProductId())
                .orElseThrow(NoSuchElementException::new);
            productSumPrice = productSumPrice.add(menuProduct.getAmount(product.getPrice()));
        }
        if (menuPrice.getValue().compareTo(productSumPrice) > 0) {
            throw new IllegalArgumentException();
        }
        menu.changePrice(menuPrice);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal productSumPrice = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            final Product product = productRepository.findById(menuProduct.getProductId())
                .orElseThrow(NoSuchElementException::new);
            productSumPrice = productSumPrice.add(menuProduct.getAmount(product.getPrice()));
        }
        if (menu.getPrice().compareTo(productSumPrice) > 0) {
            throw new IllegalStateException();
        }
        menu.changeDisplay(true);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.changeDisplay(false);
        return menu;
    }

    @EventListener
    @Transactional
    public void checkMenuPrice(ProductPriceChangedEvent event) {
        System.out.println("event = " + event.getProductId());
        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        for (final Menu menu : menus) {
            BigDecimal productSumPridce = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                final Product product = productRepository.findById(menuProduct.getProductId())
                    .orElseThrow(NoSuchElementException::new);
                productSumPridce = productSumPridce.add(menuProduct.getAmount(product.getPrice()));
            }
            if (menu.getPrice().compareTo(productSumPridce) > 0) {
                menu.changeDisplay(false);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }


}
