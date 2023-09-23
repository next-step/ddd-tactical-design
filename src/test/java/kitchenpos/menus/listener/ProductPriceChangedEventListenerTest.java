package kitchenpos.menus.listener;

import kitchenpos.common.events.ProductPriceChangedEvent;
import kitchenpos.menus.application.listener.ProductPriceChangedEventListener;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.math.BigDecimal;

import static kitchenpos.products.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;

@RecordApplicationEvents
@SpringBootTest
@DisplayName("상품 가격 변경 이벤트 발행")
class ProductPriceChangedEventListenerTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPriceChangedEventListener eventListener;


    @Autowired
    private ApplicationEvents events;

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePrice() {
        assertThat(events.stream(ProductPriceChangedEvent.class)).hasSize(0);
        final Product product = productRepository.save(product("후라이드", 16_000L));
        productService.changePrice(product.getId(), BigDecimal.valueOf(8_000L));
        assertThat(events.stream(ProductPriceChangedEvent.class)).hasSize(1);
    }

}
