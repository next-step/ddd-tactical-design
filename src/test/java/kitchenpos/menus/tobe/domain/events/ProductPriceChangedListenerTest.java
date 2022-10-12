package kitchenpos.menus.tobe.domain.events;

import kitchenpos.common.DisplayedName;
import kitchenpos.menus.tobe.domain.doubles.MemoryMenuRepository;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.doubles.FakeProfanity;
import kitchenpos.products.tobe.domain.events.ProductPriceChangedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProductPriceChangedListener.class, MemoryMenuRepository.class})
class ProductPriceChangedListenerTest {

    @Autowired
    ProductPriceChangedListener productPriceChangedListener;

    @Autowired
    MemoryMenuRepository menuRepository;

    @DisplayName("ProductPriceChangedEvent가 발행되면 해당 상품을 포함하는 메뉴의 진열여부가 변경된다.")
    @Test
    void handleProductPriceChange() {
        // given
        UUID productId = UUID.randomUUID();

        DisplayedName name = new DisplayedName(new FakeProfanity(), "호호치킨");
        MenuProduct menuProduct = new MenuProduct(productId, 1, 100_000L);
        Menu menu = new Menu(UUID.randomUUID(), name, 90_000L, 1L, true, menuProduct);

        menuRepository.save(menu);

        // when
        assertThat(menu.isDisplayed()).isTrue();
        productPriceChangedListener.handleProductPriceChange(new ProductPriceChangedEvent(productId, 80_000L));

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }
}
