package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityFilteredName;
import kitchenpos.common.domain.ProfanityFilteredNameFactory;
import kitchenpos.common.domain.Quantity;
import kitchenpos.menus.tobe.dto.MenuCreationRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.menus.tobe.domain.MenuValidator.validatePriceNotBiggerThanSumOfProducts;

@Component
public class MenuFactory {
    private static ProductRepository productRepository;
    private static MenuGroupRepository menuGroupRepository;

    @Autowired
    protected MenuFactory(final ProductRepository productRepository, final MenuGroupRepository menuGroupRepository) {
        this.productRepository = productRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    public static Menu createMenu(final MenuCreationRequest menuCreationRequest) {
        final ProfanityFilteredName profanityFilteredName = ProfanityFilteredNameFactory.createProfanityFilteredName(menuCreationRequest.getName());
        final MenuGroup menuGroup = menuGroupRepository.findById(menuCreationRequest.getMenuGroupId())
                .orElseThrow(() -> new IllegalArgumentException("cannot find menu group"));
        final Price price = Price.of(menuCreationRequest.getPrice());
        final List<MenuProduct> menuProducts = menuCreationRequest.getMenuProducts()
                .entrySet()
                .stream()
                .map(e -> createMenuProduct(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        validatePriceNotBiggerThanSumOfProducts(MenuProducts.of(menuProducts), price);

        return new Menu(profanityFilteredName, price, menuGroup, menuCreationRequest.isDisplayed(), menuProducts);
    }

    private static void validatePrice(List<MenuProduct> menuProducts, BigDecimal price) {
        Price sum = menuProducts.stream()
                .map(menuProduct -> menuProduct.getProductPrice().multiply(menuProduct.getQuantity()))
                .reduce(Price.ZERO, Price::add);

        if(sum.isLessThan(Price.of(price))) {
            throw new IllegalArgumentException("menu price cannot bigger than sum of product's sum");
        }
    }

    public static MenuProduct createMenuProduct(final UUID productId, final Quantity quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find product."));

        return new MenuProduct(product, quantity);
    }

    public static MenuGroup createMenuGroup(final String name) {
        ProfanityFilteredName profanityFilteredName = ProfanityFilteredNameFactory.createProfanityFilteredName(name);

        return new MenuGroup(profanityFilteredName);
    }
}
