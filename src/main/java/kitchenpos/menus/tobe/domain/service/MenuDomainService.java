package kitchenpos.menus.tobe.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.domain.interfaces.PurgomalumClient;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.Product;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuDomainService {

    private final PurgomalumClient purgomalumClient;
    private final ProductRepository productRepository;
    private final MenuGroupRepository menuGroupRepository;

    public MenuDomainService(
            PurgomalumClient purgomalumClient,
            ProductRepository productRepository,
            MenuGroupRepository menuGroupRepository
    ) {
        this.purgomalumClient = purgomalumClient;
        this.productRepository = productRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    public void validateDisplayedName(final String displayedName) {
        if (displayedName == null) {
            throw new IllegalArgumentException("DisplayedName는 Null이 될 수 없습니다.");
        }

        if (purgomalumClient.containsProfanity(displayedName)) {
            throw new IllegalArgumentException("DisplayedName에는 비속어가 포함 될 수 없습니다.");
        }
    }

    public void validateMenuGroup(final UUID menuGroupId) {
        menuGroupRepository.findById(menuGroupId).orElseThrow(NoSuchElementException::new);
    }

    public void validateMenuProducts(final List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (menuProducts.size() != productRepository.findAllByIdIn(parseProductIds(menuProducts)).size()) {
            throw new IllegalArgumentException();
        }
    }

    public void validateMenuPrice(final BigDecimal price, List<MenuProduct> menuProducts) {
        final List<Product> products = productRepository.findAllByIdIn(parseProductIds(menuProducts));
        final BigDecimal totalPrice = sumProductsPrice(products, menuProducts);
        if (price.compareTo(totalPrice) > 0) {
            throw new IllegalStateException();
        }
    }

    private BigDecimal sumProductsPrice(List<Product> products, List<MenuProduct> menuProducts) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(final Product product : products) {
            final long quantity = parseQuantityById(menuProducts, product.getId());
            totalPrice = totalPrice.add(product.calculatePriceWithQuantity(quantity));
        }

        return totalPrice;
    }

    private List<UUID> parseProductIds(List<MenuProduct> menuProducts) {
        return menuProducts.stream().map(MenuProduct::getProductId).collect(Collectors.toList());
    }

    private long parseQuantityById(List<MenuProduct> menuProducts, UUID productId) {
        return menuProducts.stream()
                .filter(menuProduct -> menuProduct.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getQuantity();
    }

}
