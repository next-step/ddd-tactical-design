package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.common.domain.Profanity;
import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.menu.dto.MenuDto;
import kitchenpos.menus.menu.dto.MenuProductDto;
import kitchenpos.menus.menugroup.tobe.domain.MenuGroupRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuFactory {

    public Menu create(final MenuDto menuDto, final MenuGroupRepository menuGroupRepository, final ProductRepository productRepository, final Profanity profanity) {
        validateMenuGroup(menuDto, menuGroupRepository);

        final List<MenuProduct> menuProducts = new ArrayList<>();
        final DisplayedName displayedName = DisplayedName.valueOf(menuDto.getName(), profanity);
        for (final MenuProductDto menuProductDto : menuDto.getMenuProducts()) {
            final Product product = productRepository.findById(menuProductDto.getProductId())
                    .orElseThrow(IllegalArgumentException::new);
            final MenuProduct menuProduct = MenuProduct.create(product.id(), product.price(), menuProductDto.toQuantity());
            menuProducts.add(menuProduct);
        }
        return Menu.create(
                displayedName,
                Price.valueOf(menuDto.getPrice()),
                menuDto.getMenuGroupId(),
                menuDto.getDisplayed(),
                MenuProducts.of(menuProducts));
    }

    private void validateMenuGroup(final MenuDto menuDto, final MenuGroupRepository menuGroupRepository) {
        menuGroupRepository.findById(menuDto.getMenuGroupId())
                .orElseThrow(IllegalArgumentException::new);
    }
}
