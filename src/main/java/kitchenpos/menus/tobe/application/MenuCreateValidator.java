package kitchenpos.menus.tobe.application;

import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.ui.dto.MenuCreateRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MenuCreateValidator {
    public Menu validate(final MenuCreateRequest request, final MenuGroupRepository menuGroupRepository, final ProductRepository productRepository, final Profanities profanities) {
        return new Menu(
                request.getName(profanities),
                request.getAmount(),
                validateMenuGroup(request, menuGroupRepository),
                request.isDisplayed(),
                validateMenuProducts(request, productRepository)
        );
    }

    private MenuGroup validateMenuGroup(final MenuCreateRequest request, final MenuGroupRepository menuGroupRepository) {
        return menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);
    }

    private MenuProducts validateMenuProducts(final MenuCreateRequest request, final ProductRepository productRepository) {
        final MenuProducts menuProducts = request.getMenuProducts();
        final List<UUID> productIds = menuProducts.getProductIds();
        final List<Product> products = productRepository.findAllByIdIn(productIds);
        menuProducts.validateRegistered(products);

        for(final UUID productId: productIds) {
            final Product product = productRepository.findById(productId)
                    .orElseThrow(NoSuchElementException::new);
            menuProducts.loadProduct(product);
        }
        return menuProducts;
    }

}
