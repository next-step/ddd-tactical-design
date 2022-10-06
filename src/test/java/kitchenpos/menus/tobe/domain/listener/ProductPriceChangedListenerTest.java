package kitchenpos.menus.tobe.domain.listener;

import kitchenpos.common.Events;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuName;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.doubles.FakeProfanity;
import kitchenpos.products.tobe.domain.ProductPriceChangedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {ProductPriceChangedListener.class})
class ProductPriceChangedListenerTest {

    @Autowired
    ApplicationContext applicationContext;

    @MockBean
    private MenuRepository menuRepository;

    @InjectMocks
    private ProductPriceChangedListener productPriceChangedListener;

    @BeforeEach
    void setUp() {
        Events.setPublisher(applicationContext);
    }

    @Test
    void handleProductPriceChange() {
        // given
        UUID productId = UUID.randomUUID();

        MenuName name = new MenuName(new FakeProfanity(), "호호치킨");
        MenuProduct menuProduct = new MenuProduct(productId, 1, 100_000L);
        Menu menu = new Menu(UUID.randomUUID(), name, 90_000L, 1L, true, menuProduct);

        given(menuRepository.findAllByProductId(productId)).willReturn(List.of(menu));

        // when
        assertThat(menu.isDisplayed()).isTrue();
        Events.raise(new ProductPriceChangedEvent(productId, 80_000L));

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }
}
