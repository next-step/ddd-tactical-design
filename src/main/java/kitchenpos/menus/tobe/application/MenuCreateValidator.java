package kitchenpos.menus.tobe.application;

import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.ui.dto.MenuCreateRequest;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MenuCreateValidator {
    private final MenuGroupService menuGroupService;
    private final MenuProductClient menuProductClient;
    private final Profanities profanities;

    public MenuCreateValidator(final MenuGroupService menuGroupService, final MenuProductClient menuProductClient, final Profanities profanities) {
        this.menuGroupService = menuGroupService;
        this.menuProductClient = menuProductClient;
        this.profanities = profanities;
    }

    public Menu validate(final MenuCreateRequest request) {
        return new Menu(
                request.getName(profanities),
                request.getAmount(),
                validateMenuGroup(request),
                request.isDisplayed(),
                validateMenuProducts(request)
        );
    }

    private MenuGroup validateMenuGroup(final MenuCreateRequest request) {
        return menuGroupService.findById(request.getMenuGroupId());
    }

    private MenuProducts validateMenuProducts(final MenuCreateRequest request) {
        final MenuProducts menuProducts = request.getMenuProducts();
        final List<UUID> productIds = menuProducts.getProductIds();
        final List<Product> products = menuProductClient.findAllByIdIn(productIds);
        menuProducts.validateRegistered(products);

        for(final UUID productId: productIds) {
            final Product product = menuProductClient.findById(productId);
            menuProducts.loadProduct(product);
        }
        return menuProducts;
    }

}
