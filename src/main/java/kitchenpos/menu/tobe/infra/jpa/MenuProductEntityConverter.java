package kitchenpos.menu.tobe.infra.jpa;

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
    }
}
