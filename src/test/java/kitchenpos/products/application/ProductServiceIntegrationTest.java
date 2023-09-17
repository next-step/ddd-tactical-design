package kitchenpos.products.application;

import kitchenpos.IntegrationTest;
import kitchenpos.common.exception.KitchenPosExceptionType;
import kitchenpos.products.domain.ProductPriceChangeEvent;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.dto.ChangePriceRequest;
import kitchenpos.products.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.ApplicationEvents;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.Fixtures.product;
import static kitchenpos.util.KitchenPostExceptionAssertionUtils.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductServiceIntegrationTest extends IntegrationTest {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductServiceIntegrationTest(ProductService productService,
                                         ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @DisplayName("가격 변경시 상품의 가격 변경 이벤트가 발행되어야 한다.")
    @Test
    void changePrice_success() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ChangePriceRequest request = new ChangePriceRequest(BigDecimal.valueOf(15_000L));
        final ProductDto actual = productService.changePrice(productId, request);
        assertTrue(actual.getPrice().equalValue(request.getPrice()));

        var event = applicationEvents.stream(ProductPriceChangeEvent.class)
                .filter(e -> e.getProductId().equals(productId))
                .findFirst();
        assertTrue(event.isPresent());
    }

    @DisplayName("가격 변경 중 실패시 상품의 가격 변경 이벤트가 발행되어선 안된다.")
    @Test
    void changePrice_fail() {
        final UUID productId = productRepository.save(product("후라이드", 16_000L)).getId();
        final ChangePriceRequest expected = new ChangePriceRequest(BigDecimal.valueOf(-1000L));
        assertThrows(KitchenPosExceptionType.BAD_REQUEST, () -> productService.changePrice(productId, expected));

        var event = applicationEvents.stream(ProductPriceChangeEvent.class)
                .filter(e -> e.getProductId().equals(productId))
                .findFirst();
        assertTrue(event.isEmpty());
    }

}
