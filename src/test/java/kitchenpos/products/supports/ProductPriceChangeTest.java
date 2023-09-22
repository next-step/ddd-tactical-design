package kitchenpos.products.supports;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.event.ProductPriceChangeEvent;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.Fixtures.order;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@RecordApplicationEvents
@SpringBootTest
class ProductPriceChangeTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ApplicationEvents applicationEvents;
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @Test
    void changeProductPrice() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final Product expected = changePriceRequest(BigDecimal.valueOf(15_000L));
        final Product actual = productService.changePrice(productId, expected);
        ProductPriceChangeEvent productPriceChangeEvent = applicationEvents.stream(ProductPriceChangeEvent.class).findAny().orElseThrow();
        assertThat(productPriceChangeEvent).isNotNull();
    }

    private Product changePriceRequest(final BigDecimal price) {
        return new Product(UUID.randomUUID(), "후라이드 치킨", price, purgomalumClient);
    }
}