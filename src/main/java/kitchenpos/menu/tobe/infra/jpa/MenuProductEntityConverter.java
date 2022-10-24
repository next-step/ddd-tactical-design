package kitchenpos.menu.tobe.infra.jpa;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.common.price.Price;
import kitchenpos.menu.tobe.domain.entity.Menu;
import kitchenpos.menu.tobe.domain.vo.MenuProduct;
import kitchenpos.menu.tobe.domain.vo.MenuProductQuantity;
import org.springframework.stereotype.Component;

public class MenuProductEntityConverter {

    private MenuProductEntityConverter() {
    }

    @Component
    public static class MenuProductToMenuProductEntityConverter {

        public MenuProductEntity convert(final MenuProduct menuProduct, final Menu menu) {
            final MenuProductEntity menuProductEntity = new MenuProductEntity();
            menuProductEntity.menuId = menu.id;
            menuProductEntity.productId = menuProduct.productId;
            menuProductEntity.price = menuProduct.pricePerUnit.value;
            menuProductEntity.quantity = menuProduct.quantity.value;
            return menuProductEntity;
        }

        public List<MenuProductEntity> convert(final List<MenuProduct> menuProducts, final Menu menu) {
            return menuProducts.stream()
                .map(menuProduct -> this.convert(menuProduct, menu))
                .collect(Collectors.toUnmodifiableList());
        }

        public List<MenuProductEntity> convert(final Menu menu) {
            return this.convert(menu.menuProducts(), menu);
        }
    }

    @Component
    public static class MenuProductEntityToMenuProductConverter {

        public MenuProduct convert(final MenuProductEntity menuProductEntity) {
            return new MenuProduct(
                menuProductEntity.productId,
                new Price(menuProductEntity.price),
                new MenuProductQuantity(menuProductEntity.quantity)
            );
        }

        public List<MenuProduct> convert(final Collection<MenuProductEntity> menuProductEntities) {
            return menuProductEntities.stream()
                .map(this::convert)
                .collect(Collectors.toUnmodifiableList());
        }
    }
}
