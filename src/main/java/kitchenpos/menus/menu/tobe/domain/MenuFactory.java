package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.common.domain.Profanity;
import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.menu.tobe.domain.vo.MenuProductSpecification;
import kitchenpos.menus.menu.tobe.domain.vo.MenuSpecification;
import kitchenpos.menus.menu.tobe.domain.vo.ProductSpecification;
import kitchenpos.menus.menu.tobe.domain.vo.Quantity;
import kitchenpos.menus.menugroup.tobe.domain.MenuGroupRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DomainService
public class MenuFactory {

    private final ProductContextClient productContextClient;
    private final MenuGroupRepository menuGroupRepository;
    private final Profanity profanity;

    public MenuFactory(ProductContextClient productContextClient, MenuGroupRepository menuGroupRepository, Profanity profanity) {
        this.productContextClient = productContextClient;
        this.menuGroupRepository = menuGroupRepository;
        this.profanity = profanity;
    }

    public Menu create(final MenuSpecification menuSpecification) {
        validateMenuGroup(menuSpecification.getMenuGroupId());

        final DisplayedName displayedName = DisplayedName.valueOf(menuSpecification.getName(), profanity);
        final List<MenuProduct> menuProducts = createMenuProducts(menuSpecification.getMenuProducts());
        return Menu.create(
                displayedName,
                Price.valueOf(menuSpecification.getPrice()),
                menuSpecification.getMenuGroupId(),
                menuSpecification.getDisplayed(),
                MenuProducts.of(menuProducts));
    }

    private void validateMenuGroup(final UUID menuGroupId) {
        menuGroupRepository.findById(menuGroupId).orElseThrow(IllegalArgumentException::new);
    }

    private List<MenuProduct> createMenuProducts(final List<MenuProductSpecification> menuProductSpecifications) {
        return menuProductSpecifications.stream()
                .map(this::createMenuProduct)
                .collect(Collectors.toUnmodifiableList());
    }

    private MenuProduct createMenuProduct(final MenuProductSpecification menuProductSpecification) {
        final ProductSpecification productSpecification = productContextClient.findProductById(menuProductSpecification.getProductId());
        final Price price = Price.valueOf(productSpecification.getPrice());
        final Quantity quantity = Quantity.valueOf(menuProductSpecification.getQuantity());
        return MenuProduct.create(productSpecification.getId(), price, quantity);
    }
}
