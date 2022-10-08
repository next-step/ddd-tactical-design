package kitchenpos.menus.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.domain.*;
import kitchenpos.menus.exception.MenuGroupNotFoundException;
import kitchenpos.menus.ui.request.MenuCreateRequest;
import kitchenpos.menus.ui.request.MenuProductCreateRequest;
import kitchenpos.menus.ui.response.MenuResponse;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.exception.ProductNotFoundException;
import kitchenpos.profanity.infra.ProfanityCheckClient;
import kitchenpos.reader.ProductPriceReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final ProfanityCheckClient profanityCheckClient;
    private final ProductPriceReader productPriceReader;

    public MenuService(
        MenuRepository menuRepository,
        MenuGroupRepository menuGroupRepository,
        ProductRepository productRepository,
        ProfanityCheckClient profanityCheckClient,
        ProductPriceReader productPriceReader
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
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
    public Menu changePrice(final UUID menuId, final Menu request) {
        final BigDecimal price = request.getPriceValue();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProductValues()) {
            sum = sum.add(
                productRepository.findById(menuProduct.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("ID에 해당하는 상품이 없습니다."))
                    .getPriceValue()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantityValue()))
            );
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        menu.setPrice(price);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProductValues()) {
            sum = sum.add(
                productRepository.findById(menuProduct.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("ID에 해당하는 상품이 없습니다."))
                    .getPriceValue()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantityValue()))
            );
        }
        if (menu.getPriceValue().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
        menu.setDisplayed(true);
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.setDisplayed(false);
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
